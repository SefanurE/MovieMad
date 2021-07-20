package moviemad.model;

public class Critic extends User {
	private String companyName;
	private String phoneNum;

	/**
	 * Constructor for the Critic class
	 * 
	 * @param userName
	 * @param password
	 * @param email
	 * @param country
	 * @param gender
	 * @param firstName
	 * @param lastName
	 * @param accountStatus
	 * @param accountType
	 * @param phoneNum
	 * @param companyName
	 */

	public Critic(String userName, String password, String email, String country, String gender, String firstName,
			String lastName, String accountStatus, String accountType, String phoneNum, String companyName) {

		super(userName, password, email, country, gender, firstName, lastName, accountStatus, accountType);
		this.companyName = companyName;
		this.phoneNum = phoneNum;
	}

	/**
	 * Returns the company the Critic is from
	 * 
	 * @return
	 */
	public String getCompanyName() {
		return this.companyName;
	}

	/**
	 * Returns the phone number of the Critic
	 * 
	 * @return
	 */
	public String getPhoneNum() {
		return this.phoneNum;
	}
}
