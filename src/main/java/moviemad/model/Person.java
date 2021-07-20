package moviemad.model;

import java.util.Date;

public class Person {
    private int personID;
    private String fullName;
    private String role;
    private Date birthDate;
    private String bio;

    /**
     * Constructs a Person
     * @param personID
     * @param fullName
     * @param role
     * @param birthDate
     * @param bio
     */
    public Person(int personID, String fullName, String role, Date birthDate, String bio) {
        this.personID = personID;
        this.fullName = fullName;
        this.role = role;
        this.birthDate = birthDate;
        this.bio = bio;
    }

    /**
     * Returns the ID of the person
     *
     * @return
     */
    public int getPersonID() {
        return personID;
    }

    /**
     * Returns the full name of the person
     *
     * @return
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Returns the main role of the person
     *
     * @return
     */
    public String getRole() {
        return role;
    }

    /**
     * Returns the birth date of the person
     *
     * @return
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * Returns the biography of the person
     *
     * @return
     */
    public String getBio() {
        return bio;
    }
}
