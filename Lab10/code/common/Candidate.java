package code.common;
import java.util.ArrayList;
import static code.common.Constants.*;
public class Candidate{
	/** Gender */
	private String gender;
	/** Unique ID or roll number */
	private String uid;
	/** Category of the student */
	private int category;
	/** Number of courses already applied to */
	private int appliedTo;
	/** Number of courses applied to after dereservation */
	private int appliedToD;
	/** Code of course currently enrolled in */
	private String current = "-1";
	/** Pretty name of course currently enrolled in */
	private String cleanCurrent = "";
	/** All the VirtualProgramme objects that this candidate can apply to, sorted by desirability */
	private ArrayList<VirtualProgramme> allPreferences;
	/** VirtualProgramme objects this candidate can apply to after dereservation, sorted by desirability */
	private ArrayList<VirtualProgramme> dereserved;
	/** VirtualProgramme objects this candidate can apply to before dereservation, sorted by desirability */
	private ArrayList<VirtualProgramme> preferences;
	/** Whether or not the candidate is done applying */
	public boolean isDone=false;
	/** @return 	UID of the candidate */
	public String getUID(){
		return uid;
	}
	/** @return 	current enrolled course code */
	public String getOutput(){
		return current;
	}
	/** @return 	prettified current enrolled course name */
	public String getOutputPretty(){
		if(cleanCurrent.equals("")) return "Not alloted";
		return cleanCurrent;
	}
	/** @param x	gender to be set */
	public void setGender(String x){
		gender = x;
	}
	/** @return	all preferences of this candidate */
	public ArrayList<VirtualProgramme> getPreferencesAll(){ //gale
		return allPreferences;
	}
	/** @return either all post- or pre- dereservation preferences
	@param d	if true, post-dereservation; if false, pre-dereservation */
	public ArrayList<VirtualProgramme> getPreferencesSome(boolean d){ 
		if(!d)
		return preferences;
		return dereserved;
	}
	/** @return	category of the candidate */
	public int getCategory(){
		return category;
	}
	/** notify a candidate of rejection from the most recently applied to course */
	public void getRejected(){
		appliedTo++;
		current = "-1";
		cleanCurrent = "";
	}
	/** @param c	course code of new enrolled course
	@param c2	prettified name of new enrolled course
	*/
	public void setCurrent(String c, String c2){
		current = new String(c);
		cleanCurrent = new String(c2);
	}
	/**
		Basic constructor to initialize variables and assign them from the parameters
	*/
	public Candidate(String UID, int cat){
		uid = UID;
		category = cat;
		current="-1";
		cleanCurrent = "";
		appliedTo=0;
		appliedToD = 0;
		gender = male;
		isDone = false;
		preferences = new ArrayList<VirtualProgramme>();
		dereserved = new ArrayList<VirtualProgramme>();
		allPreferences = new ArrayList<VirtualProgramme>();
	}
	/** 
	appends the given VirtualProgramme to the appropriate list	
	@param v	VirtualProgramme to be added
		@param b	iff v is a post-dereserved course
		*/
	public void addPreference(VirtualProgramme v, boolean b){
		if(!b) preferences.add(v);
		else dereserved.add(v);
	}
	/** appends the given VirtualProgramme to the list	
	@param v	VirtualProgramme to be added
	*/
	public void addPreference(VirtualProgramme v){
		allPreferences.add(v);
	}
	/** @return	all the categories this candidate can apply to */
	public ArrayList<Integer> expandCategory(){ 
		ArrayList<Integer> ret = new ArrayList<Integer>();
		switch(category){
			case gen: ret.add(gen);
					  ret.add(obc);
					  ret.add(genpd);
					  ret.add(obcpd);
					break;
			case obc: ret.add(gen);
					ret.add(obc);
					ret.add(genpd);
					ret.add(obcpd);
					break;
			case sc: ret.add(gen);
					ret.add(sc);
					ret.add(obc);
					ret.add(genpd);
					ret.add(obcpd);
					ret.add(scpd);
					break;
			case st: ret.add(gen);
					ret.add(st);
					ret.add(obc);
					ret.add(genpd);
					ret.add(obcpd);
					ret.add(stpd);
					break;
			case genpd: ret.add(gen);
						ret.add(genpd);
						ret.add(obc);
						ret.add(obcpd);
						break;
			case obcpd: ret.add(gen);
						ret.add(genpd);
						ret.add(obc);
						ret.add(obcpd);
						break;
			case scpd: ret.add(gen);
						ret.add(sc);
						ret.add(genpd);
						ret.add(scpd);
						ret.add(obc);
						ret.add(obcpd);
						break;
			case stpd: ret.add(gen);
						ret.add(st);
						ret.add(genpd);
						ret.add(stpd);
						ret.add(obc);
						ret.add(obcpd);
						break;
			case ds: //ret.add(ds);
					ret.add(gen);
					ret.add(obc);
					ret.add(genpd);
					ret.add(obcpd);
					break;
			case nat:
					ret.add(gen);
					ret.add(obc);
					ret.add(genpd);
					ret.add(obcpd);
					break;
		}
		return ret;
	}
	/** @return	all the categories this candidate can apply to post- or pre- dereservation
	@param d	iff post-dereservation is considered
	*/
	public ArrayList<Integer> expandCategory(boolean d){
		ArrayList<Integer> ret = new ArrayList<Integer>();
		switch(category){
			case gen: if(!d) ret.add(gen);
					  if(d){
						  ret.add(obc);
						  ret.add(genpd);
						  ret.add(obcpd);
					  }
					break;
			case obc: 
					if(!d){ret.add(gen);
					ret.add(obc);}
					else{
					ret.add(genpd);
					ret.add(obcpd);}
					break;
			case sc: 
					if(!d){ret.add(gen);
					ret.add(sc);}
					else{
					ret.add(obc);
					ret.add(genpd);
					ret.add(obcpd);
					ret.add(scpd);}
					break;
			case st: 
					if(!d){ret.add(gen);
					ret.add(st);}
					else{
					ret.add(obc);
					ret.add(genpd);
					ret.add(obcpd);
					ret.add(stpd);}
					break;
			case genpd: if(!d){ret.add(gen);
						ret.add(genpd);}
						else{ret.add(obc);
						ret.add(obcpd);}
						break;
			case obcpd: if(!d){ret.add(gen);
						ret.add(genpd);
						ret.add(obc);
						ret.add(obcpd);}
						break;
			case scpd:  if(!d){ret.add(gen);
						ret.add(sc);
						ret.add(genpd);
						ret.add(scpd);}
						else{
						ret.add(obc);
						ret.add(obcpd);}
						break;
			case stpd:  if(!d){ret.add(gen);
						ret.add(st);
						ret.add(genpd);
						ret.add(stpd);}
						else{
						ret.add(obc);
						ret.add(obcpd);}
						break;
			case ds: 
					if(!d)ret.add(gen);
					else{ret.add(obc);
					ret.add(genpd);
					ret.add(obcpd);}
					break;
			case nat:
					ret.add(gen);
					ret.add(obc);
					ret.add(genpd);
					ret.add(obcpd);
					break;
		}
		return ret;
	}
	/** apply to the next course in the candidate's preference list for bulk consideration
	@return		iff the candidate needs to send more applications
	*/
	public boolean applyOne(){
		if(isDone) return false;
		if(appliedTo >= allPreferences.size()) return false;
		VirtualProgramme v = allPreferences.get(appliedTo);
		v.receiveApplicationMany(this);
		return true;
	}
	/** apply to the next pre-dereservation course in the candidate's preference list for one-by-one consideration
	@return	iff the candidate needs to send more applications
	*/
	public boolean applyAll(){
		if(isDone) return false;
		if(appliedTo >= preferences.size()) return false;
		VirtualProgramme v = preferences.get(appliedTo);
		if(!v.receiveApplicationOne(this)){
			appliedTo++;
			return true;
		}
		appliedTo = 0;
		return false;
	}
	/**	set conditions back to when the candidate had not applied anywhere */
	public void reset(){
		if(isDone) return;
		appliedTo = appliedToD = 0;
		
	}
	/** apply to the next post-dereservation course in the candidate's preference list for one-by-one consideration
	@param m	the MeritList to consider while courses evaluate this candidate
	@return		iff the candidate needs to send more applications
	*/
	public boolean applyAllD(MeritList m){//merit
		if(isDone) return false;
		if(appliedToD >= dereserved.size()) return false;
		VirtualProgramme v = dereserved.get(appliedToD);
		if(!v.receiveApplicationD(this, m)){
			appliedToD++;
			return true;
		}
		return false;
	}
}
