package code.Main;
import code.gale.GaleShapelyAdmission;
import code.merit.MeritOrderAdmission;
import java.io.*;
public class Main{
	/** the main function which allocates seats according to both algorithms and outputs the results in the specified folder
	 * 
	 *Accepted usage:
	 *1) java Main.Main N
	 *To check testcase in folder "N"
	 *("N" need not be integer)
	 *2) java Main.Main N M
	 *To check all testcases in folders with names ranging from "N" to "M" 
	 *(both inclusive), 
	 *where N and M are both integers

	 * 
	 */
	public static void main(String[] args){
		if(args.length == 0){
			System.err.println("Accepted usage:\n1) java Main.Main N\n\t To check testcase in folder \"N\"\n\t(\"N\" need not be integer)\n2) java Main.Main N M\n\t To check all testcases in folders with names ranging from \"N\" to \"M\" \n\t(both inclusive), \n\twhere N and M are both integers");
			return;
		}	
		if(args.length==1){
			try{
				GaleShapelyAdmission.main(args);
				MeritOrderAdmission.main(args);
			}
			catch(IOException e){
				System.err.println("Error in processing I/O");
			}
			return;
		}
		int a = Integer.parseInt(args[0]);
		int b = Integer.parseInt(args[1]);
		for(int i=a; i<=b; i++){
			String[] f= new String[1];
			f[0]=Integer.toString(i);
			try{
				GaleShapelyAdmission.main(f);
				MeritOrderAdmission.main(f);
			}
			catch(IOException e){
				System.err.println("Error in processing I/O");
			}
		}
	}
}

