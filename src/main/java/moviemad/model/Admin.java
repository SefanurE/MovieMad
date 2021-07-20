package moviemad.model;

public class Admin extends User{

    /**
     * Constructs an Admin
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
    public Admin(String username, String password, String email, String country, String gender, String firstName,
                 String lastName, String accountStatus, String accountType) {
        super(username,password, email, country,gender,firstName,lastName,accountStatus,accountType);
    }

}
