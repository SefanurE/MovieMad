package moviemad.model;

public class User {
	private String username;
	private String password;
	private String email;
	private String country;
	private String gender;
	private String firstName;
	private String lastName;
	private String accountStatus;
	private String accountType;

	/**
	 * Constructs a User
	 * @param username
	 * @param password
	 * @param email
	 * @param country
	 * @param gender
	 * @param firstName
	 * @param lastName
	 * @param accountStatus
	 * @param accountType
	 */
	public User(String username, String password, String email, String country, String gender, String firstName,
			String lastName, String accountStatus, String accountType) {

		this.username = username;
		this.password = password;
		this.email = email;
		this.country = country;
		this.gender = gender;
		this.firstName = firstName;
		this.lastName = lastName;
		this.accountStatus = accountStatus;
		this.accountType = accountType;
	}

	/** 
	 * Returns the username of the user
	 * 
	 * @return
	 */
	public String getUsername() {
		return username;
	}

	/** 
	 * Returns the password of the user
	 * 
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	/** 
	 * Returns the email of the user
	 * 
	 * @return
	 */
	public String getEmail() {
		return email;
	}

	/** 
	 * Returns the country of the user
	 * 
	 * @return
	 */
	public String getCountry() {
		return country;
	}

	/** 
	 * Returns the gender of the user
	 * 
	 * @return
	 */
	public String getGender() {
		return gender;
	}

	/** 
	 * Returns the first name of the user
	 * 
	 * @return
	 */
	public String getFirstName() {
		return firstName;
	}

	/** 
	 * Returns the last name of the user
	 * 
	 * @return
	 */
	public String getLastName() {
		return lastName;
	}

	/** 
	 * Returns the account status of the user
	 * 
	 * @return
	 */
	public String getAccountStatus() {
		return accountStatus;
	}
	
	/** 
	 * Returns the account type of the user
	 * 
	 * @return
	 */
	public String getAccountType() {
		return accountType;
	}

}
