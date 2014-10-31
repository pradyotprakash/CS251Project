package code.common;
public class Constants{
	/** Default folder for testcases */	
	public static final String testcases = "test_cases";
	/** To toggle debug statements during testing */
	public static final boolean debug=false;
	/** Default name for file containing courses information */
	public static final String courseFile = "programs.csv";
	/** Default name for file containing preferences information */
	public static final String studentFile = "choices.csv";
	/** Default name for file containing ranklists */
	public static final String rankFile = "ranklist.csv";
	/** Default output destination */
	public static final String outputFile = "GaleShapelyAdmission.csv";
	/** Default output destination */
	public static final String outputFile2 = "MeritOrderAdmission.csv";
	/** Default output destination */
	public static final String prettyFile = "GaleShapelyAdmissionPretty.csv";
	/** Default output destination */
	public static final String prettyFile2 = "MeritOrderAdmissionPretty.csv";
	/** Constants for the possible categories */
	public static final int gen = 0, obc = 1, sc = 2, st = 3, genpd = 4, obcpd = 5, scpd = 6, stpd = 7, ds = 8, nat = 9;
	/** Constants for the genders */
	public static final String male = "male", female = "female";
	/**
	@param n1	Birth Category (GE, SC, OBC, ST, DS, F)
	@param n2	PwD Category (Y, N)
	@return		Integer value corresponding to category
	*/
	public static int getCategory(String n1, String n2){
		int c=0;
		switch(n1){
			case "GE": c=gen; break;
			case "SC": c= sc; break;
			case "OBC": c= obc; break;
			case "ST": c= st; break;
			case "DS": c= ds; break;
			case "F" : c=nat; break;
			default:
				System.err.println("Error parsing category");
				System.exit(0);
		}
		if(c>7) return c;
		switch(n2){
			case "Y": c+=4; break;
			case "N": break;
			default:
				System.err.println("Error parsing PD status");
				System.exit(0);
		}
		return c;
	}
	/**
	@param x	The integer value of the category
	@return		String corresponding to the category
	*/
	public static String getCategoryName(int x){
		switch(x){
			case gen: return "GE";
			case obc: return "OBC";
			case sc: return "SC";
			case st: return "ST";
			case genpd: return "GE-PD";
			case obcpd: return "OBC-PD";
			case scpd: return "SC-PD";
			case stpd: return "ST-PD";
			case nat: return "F";
			case ds: return "DS";
		}
		return "-1";
	}
	
}
