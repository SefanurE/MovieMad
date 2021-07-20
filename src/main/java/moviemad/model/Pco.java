package moviemad.model;

public class Pco extends User {
	private String companyName;
	private int prodID;
	private String phoneNum;

	/**
	 * Constructs a Production Company
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
	 * @param prodCoName
	 * @param prodID
	 */
	public Pco(String userName, String password, String email, String country, String gender,
			String firstName, String lastName, String accountStatus, String accountType, String phoneNum,
			String prodCoName, int prodID) {

		super(userName, password, email, country, gender, firstName, lastName, accountStatus, accountType);
		this.companyName = prodCoName;
		this.phoneNum = phoneNum;
		this.prodID = prodID;

	}

	/**
	 * Returns the Production Company's name
	 * 
	 * @return
	 */
	public String getCompanyName() {
		return this.companyName;
	}

	/**
	 * Returns the Production Company's phone number
	 * 
	 * @return
	 */
	public String getPhoneNum() {
		return this.phoneNum;
	}

	/**
	 * Returns the Production Company's ID on shows
	 *
	 * @return
	 */
	public int getProdID() {
		return this.prodID;
	}
}
