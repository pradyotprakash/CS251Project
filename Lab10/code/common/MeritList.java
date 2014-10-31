package code.common;
import java.util.HashMap;
import static code.common.Constants.*;
public class MeritList{
	/** Fast lookup of candidate's ranks */
	private HashMap<Candidate, Float> ranking;
	/** @return	rank of the candidate
	@param c	Candidate in this MeritList whose rank is queried */
	public Float getRank(Candidate c){
		return ranking.get(c);
	}
	/** @return	iff the candidate is present in this MeritList
	@param s	Candidate to be checked
	*/
	public boolean contains(Candidate s){
		return ranking.containsKey(s);
	}
	/** debug utility */
	public void print(){
		if(!debug) return;
		for(Candidate c : ranking.keySet())
			System.out.println(c.getUID() + " has rank " + ranking.get(c));
	}
	/** @return	complete ranklist that this MeritList holds */
	public HashMap<Candidate, Float> getRanklist(){
		return ranking;
	}
	/** @return	iff c1 is a better candidate than c2
	@param c1 	Candidate to be compared
	@param c2	Candidate to be compared */
	public boolean compare(Candidate c1, Candidate c2){
		if(!contains(c1)) return false;
		if(!contains(c2)) return true;
		return ranking.get(c1) < ranking.get(c2);
	}
	/** @return	iff c1 is as good a candidate as c2
	@param c1 	Candidate to be compared
	@param c2	Candidate to be compared */
	public boolean equal(Candidate c1, Candidate c2){
		if(!contains(c1)) return false;
		if(!contains(c2)) return false;
		
		return ranking.get(c1) == ranking.get(c2);
	}
	/** adds a new candidate to the current ranklist
	@param c	Candidate to be added
	@param r	rank of the new candidate
	*/
	public void addCandidate(Candidate c, float r){
		ranking.put(c, r);
	}
	/** basic constructor to initialize the ranklist */
	public MeritList(){
		ranking = new HashMap<Candidate, Float>();
	}
	/** appends a ranklist to this one; all candidates in the latter will be strictly worse than all candidates in the former 
	@param m	MeritList to be appended to this
	*/
	public void append(MeritList m){
		int s = ranking.size();
		for(Object o : m.getRanklist().keySet().toArray()){
			Candidate c = (Candidate) o;	
			if(ranking.containsKey(c)) continue;		
			addCandidate(c, s + m.getRank(c));
		}
	}
}
