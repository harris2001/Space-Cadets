package exercise1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;

public class Exercise1 {
	public static void main(String[] args) throws Exception {
		/*System.getProperties().put("proxySet", "true");
		System.getProperties().put("proxyHost", "152.78.128.51");
		System.getProperties().put("proxyPort", "3128");
		*/
		BufferedReader br = null; // initializing the BufferedReader object
		
		br = new BufferedReader(new InputStreamReader(System.in)); //reading from standard input
		
		String email_id = br.readLine(); // passing the input to email_id
		
		URL url = new URL("https://www.ecs.soton.ac.uk/people/"+email_id); //setting the url of the user profile
		
		URLConnection con = url.openConnection();
		con.setConnectTimeout(999);
		
		BufferedReader html = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));//opening the url stream and passing it to the BufferedReader object through standared input
		String important_line=null;
		int counter = 0;
		
		while((important_line = html.readLine())!=null) {
			if(counter==84) {
				break;
			}
			counter++;
		}
		String identify = "property=\"name\">";
		int index = important_line.indexOf(identify);
		int index2 = important_line.indexOf("<em property=");
		System.out.print(important_line.substring(index+identify.length(),index2));
	    
	}
}
