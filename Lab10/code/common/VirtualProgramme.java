package code.common;
import java.util.*;
import static code.common.Constants.*;
public class VirtualProgramme{
	/** VirtualProgramme code*/
	public String name;
	/** Prettified name of the VirtualProgramme */
	public String cleanName;
	/** Category of the VirtualProgramme */
	private int category;
	/** Maximum number of students to enrol */
	private int capacity;
	/** Number of students currently enrolled */
	private int selected;
	/** Meritlist to use to compare candidates */
	private MeritList ranking;
	/** Rank of the worst candidate taken so far, pre- and post- dereservation */
	public float worst, worstD;
	/** Waitlisted candidates */
	private ArrayList<Candidate> waitlist;
	/** Applications received */
	private ArrayList<Candidate> applications;
	/** @return category of this VirtualProgramme */
	public int getCategory(){
		return category;
	}
	/** Receives applications in bulk, from this candidate 
	@param c	Candidate to reveive applications from
	*/
	public void receiveApplicationMany(Candidate c){
		if(debug) System.out.println(name + " (" + category + ") " +  " receives application from " + c.getUID());
			
		applications.add(c);
	}
	/** Receives applications in bulk, from this candidate 
	@param c	Candidate to reveive applications from
	@return iff the candidate is selected
	*/
	public boolean receiveApplicationOne(Candidate c){
		if(debug) System.out.println(name + " (" + category + ") " +  " receives application from " + c.getUID());			
		if(!ranking.contains(c)) return false;
		if(selected < capacity || worst == ranking.getRank(c)){
			if(worst < ranking.getRank(c))
			worst = ranking.getRank(c);
			selected++;
			c.setCurrent(name, "IIT" + name.charAt(0) + " " + cleanName + " " + getCategoryName(category));
			c.isDone=true;
			return true;
		}
		c.isDone=false;
		return false;
	}
	/** @return number of seats in this program */
	public int getSeatsLeft(){
		return capacity;
	}
	/** Receives applications (after dereservation) in bulk, from this candidate 
	@param c	Candidate to reveive applications from
	@return iff the candidate is selected
	*/
	
	public boolean receiveApplicationD(Candidate c, MeritList m){
		if(debug) System.out.println(name + " (" + category + ") " +  " receives application from " + c.getUID());			
		if(!m.contains(c)) {c.isDone = false; return false;}
		
		if(selected < capacity || worstD == m.getRank(c)){
			if(worstD < m.getRank(c))
			worstD = m.getRank(c);
			selected++;
			c.setCurrent(name, "IIT" + name.charAt(0) + " " + cleanName + " " + getCategoryName(category));
			c.isDone=true;
			return true;
		}
		c.isDone=false;
		return false;
	}
	/** process all the applications received by the VirtualProgramme
	@return iff any candidates have had to be rejected
	 
	 */
	public boolean filter(){
		boolean rejected = false;
		if(applications.size() == 0) return false;
		waitlist.clear();
		Collections.sort(applications, new Comp() );
		int i=0;
		worst=-1;
		int selected = 0;	
		for(i=0; i<applications.size(); i++){
			Candidate c = applications.get(i);
			if(! ranking.contains(c)){
				if(debug) System.out.println(name + " (" + category + ") " + " does not have " + c.getUID());
				c.getRejected();
				rejected = true;
				continue;
			}
			if(selected < capacity || ranking.getRank(c) == worst){
				c.setCurrent(name, "IIT"+name.charAt(0)+" "+cleanName + " " + getCategoryName(category));
				selected++;
				if(debug) System.out.println(name + " (" + category + ") " + " waitlists " + c.getUID());
				waitlist.add(c);
				if(ranking.getRank(c) > worst)
				worst = ranking.getRank(c);
			}
			else{
				if(debug) System.out.println(name + " (" + category + ") " + " rejects " + c.getUID());
				c.getRejected();
				rejected = true;
			}
		}
		applications.clear();
		return rejected;
	}
	/** basic constructor for initializing member variables */
	public VirtualProgramme(String n, int cat, int cap, MeritList m, String cn){
		name = n;
		cleanName = cn;
		category = cat;
		capacity = cap;
		ranking = m;
		worst = -1;
		worstD = -1;
		waitlist = new ArrayList<Candidate>();
		applications = new ArrayList<Candidate>();
		
	}
	/** private class used in ordering the Candidates having had applied to this VirtualProgramme */
	private class Comp implements Comparator<Candidate>{
		public int compare(Candidate c1, Candidate c2){
			if(!ranking.contains(c1)) return 1;
			if(!ranking.contains(c2)) return -1;
			if(ranking.compare(c1, c2)) return -1;
			return 1;
		}
	}
	
}

