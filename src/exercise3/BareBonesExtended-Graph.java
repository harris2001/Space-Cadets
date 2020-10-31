import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;

public class test1 {

	HashMap<String, Integer> var = new HashMap<String, Integer>(); //variables array
	Tree root = new Tree(); //parent node
	Tree node = new Tree(); //child node
	ArrayList<String> instr_arr = new ArrayList<String>();	

	public static void main(String[] args) {
		test1 c = new test1();
		c.run();
	}
	public void run(){
		boolean check = true;
		
		//while(check){

			//System.out.println("Choose Bare Bone to execute");
			//System.out.print(">>>>");
			Scanner sc = new Scanner(System.in);

			try{
				File f_obj = new File("bones.txt");//sc.nextLine());
				sc = new Scanner(f_obj);
				
				String[] first_instr = {"while","~"}; //first instruction
				var.put(first_instr[1],1); // ~ = 1s
				root.instr = first_instr;
				root.children.add(node); //adding child to the root node

				while(sc.hasNextLine()){

					String[] ss = sc.nextLine().trim().replaceAll(" +"," ").split(";");
					
					for(int i=0; i<ss.length; i++){

						ss[i] = ss[i].trim();
						
						//excluding single line comments
						if(ss[i].contains("//")){
							break;
						}
						//System.out.println("Storing: "+ss[i]);
						store(ss,i);
					}	
				}
				dfs(root,0);
				check = false;
			}
			catch(Exception e){
				System.out.println("File Not Found, please try again");
			}
		//}
	}

	void store(String[] ss, int i) {
		System.out.println(">>>>>: "+ss[i]);
		instr_arr.add(ss[i]);
/*
		System.out.println("Storing " + ss[i]);
		
		node.instr = ss; //storing instruction

		if(ss[0].equals("while")){
			Tree child = new Tree(); //creating a child 
			node.children.add(child); //assigning children to parent
			node = child; //changing current node to the child
		}*/

	}

	void dfs(Tree node,int k){

		Tree child = new Tree();

		String instr[] = instr_arr.get(k).split(" ");
		child.instr = instr;
		System.out.println(child.instr[0]);
		node.children.add(child);
		//System.out.println("Child:"+node.children.get(1).instr[0]);

		if(instr[0].equals("while")) {
			String ws[] = instr; // while instruction 
			System.out.println("Checking");
			boolean condition = check(ws); //checking the while instruction
			while(condition==true){
				System.out.println("Children size: "+node.children.size());
				int kk = k;
				dfs(child,kk+1);
				//for(int i=1; i<node.children.size(); i++){
					//System.out.println("Instr: "+node.children.get(i).instr[0]);
				//}
				///////////////write decr the coutner/////////////////
				var.put(ws[1],var.get(ws[1])-1);
				condition = check(ws); //checking the while instruction
			}
		}
		if(instr[0].equals("end")){
			return;
		}
		if(instr[0].equals("clear")){
			var.put(instr[1],0);	
			//System.out.println(var.get(instr[1]));
			dfs(node,k+1);
		}
		if(instr[0].equals("incr")){
			var.put(instr[1],var.get(instr[1])+1);
			//System.out.println(var.get(instr[1]));
			dfs(node,k+1);
		}
		if(instr[0].equals("decr")){
			var.put(instr[1],var.get(instr[1])-1);
			//System.out.println(var.get(instr[1]));
			dfs(node,k+1);
		}
		if(instr[0].equals("print")){
			//System.out.println(var.get(instr[1]));
			dfs(node,k+1);
		}
	}

	boolean check(String[] op){

		int a,b;
		a = var.get(op[1]);
		if(op.length<3){
			return a!=0;
		}
		try{
			b = var.get(op[3]);
		}
		catch(Exception e){
			b = Integer.parseInt(op[3]);
		}
		boolean ret = false;
		switch(op[2]) {
			case "<": ret = a<b; break;
			case "<=": ret = a<=b; break;
			case ">": ret = a>b; break;
			case ">=": ret = a>=b; break;
			case "==": ret = a==b; break;
			case "not": ret = a!=b; break;
			case "!": ret = a!=b; break;
			case "!=": ret = a!=b; break;
			case "&&":
				if(a!=0 && b!=0){
					ret = true;
				}
				else{
					ret = false;
				}
				break;
			case "||":
				if(a!=0 || b!=0){
					ret = true;
				}
				else{
					ret = false;
				}
				break;
			default:
				ret = a!=0;
		}
		System.out.println(">>>" + ret);
		return ret;
	}

	int operation(String[] op){
		//while()
		return 1;
	}

	/*int exist(HashMap<String,Integer> v){
		for(value : v){
			if(value)
		}
	}*/

}

public class Tree {
	String[] instr;	
	ArrayList<Tree> children = new ArrayList<Tree>();
}
