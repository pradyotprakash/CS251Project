package code.merit;
import java.util.*;
import java.io.*;
import static code.common.Constants.*;
import code.common.*;
public class MeritOrderAdmission{
	/** stores all the courses available*/
	private HashMap<String, ArrayList<VirtualProgramme> > courses;
	/** a list of all the DS category students */
	private ArrayList<Candidate> dsStudents;
	/** a list of all the foreign national students */
	private ArrayList<Candidate> natStudents;
	/** stores the number of DS students accepted so far at each institute */
	private HashMap<Character, Integer> institutes;
	/** stores all the students to consider */
	private HashMap<String, Candidate> students;
	/** a two dimensional array of all the candidates arranged according to category */
	private ArrayList<ArrayList<Candidate> > studentLists;
	/** the various ranklists of students dependign on category */
	private ArrayList<MeritList> rankLists;
	/** a list of all the students in the order they were read */
	private ArrayList<Candidate> initialOrder;
	/** the base folder where testcases reside */
	private File base;
	/** read input from all the files for the current testcase */
	
	private void takeInput(){
		initialOrder = new ArrayList<Candidate>();
		rankLists = new ArrayList<MeritList>();
		for(int i=0; i<8; i++) rankLists.add(new MeritList());
		dsStudents = new ArrayList<Candidate>();
		natStudents = new ArrayList<Candidate>();
		studentLists = new ArrayList<ArrayList<Candidate> >();
		for(int i=0; i<8; i++)
			studentLists.add(new ArrayList<Candidate>());
		students = new HashMap<String, Candidate>();
		courses  = new HashMap<String, ArrayList<VirtualProgramme> >();
		institutes = new HashMap<Character, Integer>();
		//initialize courses
		{
			File course = new File(base, courseFile);
			BufferedReader br=null;
			try{
				br = new BufferedReader(new FileReader(course));
			}
			catch(FileNotFoundException e){
				System.err.println("courses file not found!");
				System.exit(0);
			}
			String line="";
			boolean first=true;
			while(true){
				try{
					line = br.readLine();
				}
				catch(IOException e){
					System.err.println("Error in reading from courses file!");
					System.exit(0);
				}
				if(first){
					first=false;
					continue;
				}
				if(line == null)
					break;
				if(line.equals(""))
					break;
				String[] info = line.split(",");
				{
					
					ArrayList<VirtualProgramme> list = new ArrayList<VirtualProgramme>();
					for(int i=3; i < info.length; i++){
						VirtualProgramme v = new VirtualProgramme(info[1], i-3, (int)Float.parseFloat(info[i]), rankLists.get(i-3), info[2]);
						list.add(v);
					}
					
					courses.put(info[1], list);
					institutes.put(info[1].charAt(0), 0);
					
				}
				line="";
			}
			try{
				br.close();
			}
			catch(IOException e){
				System.err.println("courses file not closed!");
				System.exit(0);
			}
		}
		//initialize students
		
		{
			File course = new File(base, studentFile);
			BufferedReader br=null;
			try{
				br = new BufferedReader(new FileReader(course));
			}
			catch(FileNotFoundException e){
				System.err.println("students file not found!");
				System.exit(0);
			}
			String line="";
			boolean first = true;
			while(true){
				try{
					line = br.readLine();
				}
				catch(IOException e){
					System.err.println("Error in reading from students file!");
					System.exit(0);
				}
				if(first){
					first=false;
					continue;
				}
				if(line == null)
					break;
				if(line.equals(""))
					break;
				String[] info = line.split(",");
				Candidate c = new Candidate(info[0], getCategory(info[1], info[2]));
				String[] prefs = info[3].split("_");
				for(String dept : prefs){
					for(int i: c.expandCategory(false))
						c.addPreference(courses.get(dept).get(i), false);
					for(int i: c.expandCategory(true))
						c.addPreference(courses.get(dept).get(i), true);
				}
				if(c.getCategory() == 8) dsStudents.add(c);
				if(c.getCategory() == 9) natStudents.add(c);
				
				students.put(info[0], c);
				initialOrder.add(c);
			}
			try{
				br.close();
			}
			catch(IOException e){
				System.err.println("students file not closed!");
				System.exit(0);
			}
		}	
		//initialize ranklists
		{
			File course = new File(base, rankFile);
			BufferedReader br=null;
			try{
				br = new BufferedReader(new FileReader(course));
			}
			catch(FileNotFoundException e){
				System.err.println("ranks file not found!");
				System.exit(0);
			}
			String line="";
			boolean first=true;
			while(true){
				try{
					line = br.readLine();
				}
				catch(IOException e){
					System.err.println("Error in reading from ranks file!");
					System.exit(0);
				}
				if(first){
					first=false;
					continue;
				}
				if(line == null)
					break;
				if(line.equals(""))
					break;
				String[] info = line.split(",");
				students.get(info[0]).setGender((info[1]));
				for(int i=0; i < info.length; i++){
					if(i==0 || i==1 || i==2 || i==7) continue;
					if(Float.parseFloat(info[i])==0) continue;
					int k = i-3;
					if(i>=7) k--;
					rankLists.get(k).addCandidate(students.get(info[0]), Float.parseFloat(info[i]));
					studentLists.get(k).add(students.get(info[0]));
				}
			}
		}
			
		//append ranklists
		{
			rankLists.get(obc).append(rankLists.get(gen));
			rankLists.get(genpd).append(rankLists.get(gen));
			rankLists.get(obcpd).append(rankLists.get(gen));
			rankLists.get(scpd).append(rankLists.get(sc));
			rankLists.get(stpd).append(rankLists.get(st));
			
		}
		//sort DS and Nat students
		Collections.sort(natStudents, new Comp());
		Collections.sort(dsStudents, new Comp());
	}
	/** process the admissions for DS category students */
	private void dsAllotment(){
		for(Candidate c : dsStudents){
			for(VirtualProgramme v : c.getPreferencesSome(false)){
				if(institutes.get(v.name.charAt(0)) < 2){
					c.setCurrent(v.name, "IIT"+v.name.charAt(0)+" " +v.cleanName + " " + getCategoryName(ds));
					c.isDone = true;
					int a = institutes.get(v.name.charAt(0));
					institutes.put(v.name.charAt(0), a+1);
					
					break;
				}
			}
		}
	}
	/** process the admissions for the foreign national category studnets */
	private void natAllotment(){
		for(Candidate c: natStudents){
			for(VirtualProgramme v : c.getPreferencesSome(false)){
				if(v.getSeatsLeft() == 0) continue;
				if(v.worst == -1 || rankLists.get(gen).getRank(c) <= v.worst){
				c.setCurrent(v.name, "IIT"+v.name.charAt(0)+" " +v.cleanName + " " + getCategoryName(v.getCategory()) + " F");
					c.isDone = true;
					break;
				}
			}
		}
	}
	/** process the admissions for all the Indian students who did not get a DS seat */
	private void normalAllotment(){
		for(int i=0; i<8; i++)
			Collections.sort(studentLists.get(i), new Comp2(i));
		if(debug) System.out.println("Before dereservation:");
		for(int i=0; i<8; i++){
			for(Candidate c: studentLists.get(i)){
				if(c.getCategory() == nat) continue;
				if(c.isDone) continue;
				boolean t=true;
				while(t)
					t=c.applyAll();
				
				c.reset();
			}			
		}
		if(debug) System.out.println("After dereservation");
		
			
		for(int i=0; i<8; i++){
			for(Candidate c: studentLists.get(i)){
				if(c.getCategory() == nat) continue;
				if(c.isDone) continue;
				boolean t=true;
				while(t){
					switch(c.getCategory()){
						case gen: t= c.applyAllD(rankLists.get(gen));
									break;
						case obc: t = c.applyAllD(rankLists.get(gen));
									break;
						case sc: t = c.applyAllD(rankLists.get(sc));
									break;
						case st: t = c.applyAllD(rankLists.get(st));
									break;
						case genpd: t=c.applyAllD(rankLists.get(gen));
									break;
					}
				}
			}
		}
		
	}					
	/** print the output of the admissions process to the file */
	private void output() throws IOException{
		File f = new File(base, outputFile2);
		BufferedWriter br = new BufferedWriter(new FileWriter(f));	
		br.write("CandidateUniqueId,ProgrammeID");
		br.newLine();
		for(Candidate c : initialOrder){
			br.write(c.getUID() + ", " + c.getOutput());
			br.newLine();
		}
		br.close();
	}
	/** print the output of the admissions process to the file in a human-readable format*/
	private void outputPretty() throws IOException{
		File f = new File(base, prettyFile2);
		BufferedWriter br = new BufferedWriter(new FileWriter(f));	
		br.write("CandidateUniqueId,ProgrammeID");
		br.newLine();
		for(Candidate c : initialOrder){
			br.write(c.getUID() + ", " + c.getOutputPretty());
			br.newLine();
		}
		br.close();
	}
	/** simple function for debugging purpose */
	private void sanityCheck(){
		if(!debug) return;
		int i=0;
		for(MeritList m : rankLists){
			System.out.println("Merit list " + i++);
			m.print();
			
		}
	}		
	/** the main function that takes care of all the admissions process for all students */	
	public static void main(String[] args) throws IOException{
		MeritOrderAdmission mo = new MeritOrderAdmission();
		
		File dot = new File(".");
		File testdir = new File(dot, testcases);
		File base = new File(testdir, args[0]);
		mo.base = base;
		mo.takeInput();
		//mo.sanityCheck();
		mo.dsAllotment();
		mo.normalAllotment();
		mo.natAllotment();
		try {mo.output();mo.outputPretty();}
		catch(IOException e){
			System.err.println("Error in showing output");
		}
	}		
	private class Comp implements Comparator<Candidate>{
		public int compare(Candidate c1, Candidate c2){
			if(rankLists.get(gen).compare(c1, c2)) return -1;
			return 1;
		}
	}
	private class Comp2 implements Comparator<Candidate>{
		private int x;
		public Comp2(int y){
			x=y;
		}
		public int compare(Candidate c1, Candidate c2){
			if(rankLists.get(x).compare(c1, c2)) return -1;
			return 1;
		}
	}
}
