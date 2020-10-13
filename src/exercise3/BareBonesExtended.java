package exercise3;
import java.io.*;
import java.util.*;


public class BareBonesExtended {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		//reading file
		File file_obj = new File("bone2.txt");
		try {
			Scanner sc = new Scanner(file_obj);
			BareBonesExtended bb = new BareBonesExtended();
			bb.Interpreter(sc);
		}
		//if file doesn't exist, throw exeption
		catch (FileNotFoundException e) {
			System.out.println("An error occured while reading file");
			e.printStackTrace();
		}
	}
	
	void Interpreter(Scanner sc) {
		Vector<variable> variables = new Vector<variable>();
		Vector<String[]> instructions = new Vector<String[]>(); 
		Vector<String>counters = new Vector<String>();
		Vector<Integer>s_pointers = new Vector<Integer>();
		
		variable var;

		//We begin with a large loop (while ~ == 1)
		
		//null variable
		var = new variable();
		var.name="!";
		variables.add(var);
		
		//~ = 1
		var = new variable();
		var.name="~";
		var.value=1;
		variables.add(var);
		counters.add("~");
		//starting from instruction at position 0
		s_pointers.add(0);
		
		//while ~= 1 (repeat every instruction in the file once)
		String[] starting = new String[2];
		starting[0]="while";
		starting[1]="~";
		instructions.add(starting);
		
		while(sc.hasNextLine()) {
			String[] instr = sc.nextLine().trim().split("\\s");
			if(instr[0].equals("end")) {
				instr = new String[2];
				instr[0]="end";
				instr[1] = ";";

			}
			instructions.add(instr);
		}
		String[] ending_instr = new String[2];
		ending_instr[0] = "end";
		ending_instr[1] = ";";
		instructions.add(ending_instr);
		
		return_type ret= new return_type();
		ret = Compile(variables,instructions,counters,s_pointers);
		System.out.println("oo");
		print_results(ret.variables);
	}

	return_type Compile(Vector<variable> variables, Vector<String[]> instructions,Vector<String> counters,Vector<Integer>s_pointers) {
		String[] instr;
		//print_results(variables);
		int esp=s_pointers.lastElement();
		System.out.println(">>>>>>>"+instructions.get(esp)[0]+" "+instructions.get(esp)[1]);
		System.out.println("Executing "+Get_variable(variables,instructions.get(esp)[1]).value+" times");
		int ebp;
		int r_esp=esp;
		while(Get_variable(variables,instructions.get(esp)[1]).value>0) {
			ebp = esp;
			esp++;
			do{
				instr=instructions.get(esp);
				System.out.println(instr[0]);
				//System.out.println(instr[0]+" "+instr[1]);
				if(instr[0].equals("while")) {
					s_pointers.add(esp);
					//System.out.println("adding pointer: "+(esp));
					counters.add(instr[1]);
					return_type ret = new return_type();
					ret = Compile(variables,instructions,counters,s_pointers);
					variables = ret.variables;
					instructions = ret.instructions;
					counters = ret.counters;
					esp = ret.esp;
					s_pointers = s_pointers;
					s_pointers.remove(s_pointers.lastElement());
					//System.out.println(s_pointers.lastElement()+" "+esp);
				}
				if(instr[0].equals("clear")) {
					variable vv = Get_variable(variables,instr[1].replace(";", ""));
					if(vv.name=="!") {
						variable v = new variable();
						v.name=instr[1].replace(";", "");
						v.value=0;
						variables.add(v);
					}
					else {
						vv.value=0;
					}
				}
				if(instr[0].equals("incr")) {
					Get_variable(variables,instr[1]).value++;
				}
				if(instr[0].equals("decr")) {
					Get_variable(variables,instr[1]).value--;
				}
				if(instr[0].equals("print")) {
					System.out.println(Get_variable(variables,instr[1]).value);
				}
				esp++;
			}while(!instr[0].equals("end"));
			if(Get_variable(variables,counters.get(1)).value==0) {
				//System.out.println("EXITING");
				break;
			}
			r_esp = esp;
			esp=ebp;
		}
		return_type ret = new return_type();
		ret.init(variables,instructions,counters,s_pointers,r_esp);

		/*System.out.println("Returning:");
		System.out.println("____________________________");*/
		
		//print_results(variables);
		return ret;
	}
	
	void print_results(Vector<variable> variables) {
		for(int i=2; i<variables.size(); i++) {
			String name = variables.get(i).name;
			Integer value = variables.get(i).value;
			System.out.println(name.replace(";","")+" whith value "+value);
		}
	}
	
	variable Get_variable(Vector<variable> variables,String name) {
		for(int i=0; i<variables.size(); i++) {
			if(variables.get(i).name.equals(name)) {
				return variables.get(i);
			}
		}
		return variables.get(0);
	}
	
}
class variable{
	String name;
	Integer value;
}
class loop{
	int start=0;
	int stop=0;
}
class return_type{
	Vector<variable> variables;
	Vector<String[]> instructions;
	Vector<String> counters;
	Vector<Integer> s_pointers;
	Integer esp;
	void init(Vector<variable> variables, Vector<String[]> instructions,Vector<String> counters,Vector<Integer>s_pointers,Integer esp) {
		this.variables = variables;
		this.instructions = instructions;
		this.counters = counters;
		this.s_pointers = s_pointers;
		this.esp = esp-1;
	}
}