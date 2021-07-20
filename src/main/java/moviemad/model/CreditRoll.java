package moviemad.model;

public class CreditRoll {
    private int personID;
    private String role;
    private int showID;
    private int startYear;
    private int endYear;
    private String characterName;

    /**
     * Constructs a Credit Roll
     * @param personID
     * @param role
     * @param showID
     * @param startYear
     * @param endYear
     * @param characterName
     */
    public CreditRoll(int personID, String role, int showID, int startYear, int endYear, String characterName) {
        this.personID = personID;
        this.role = role;
        this.showID = showID;
        this.startYear = startYear;
        this.endYear = endYear;
        this.characterName = characterName;
    }

    /**
     * Returns the ID of the person credited
     *
     * @return
     */
    public int getPersonID() {
        return personID;
    }

    /**
     * Returns the role being credited
     *
     * @return
     */
    public String getRole() {
        return role;
    }

    /**
     * Returns the ID of the show
     * related to the credit
     *
     * @return
     */
    public int getShowID() {
        return showID;
    }

    /**
     * Returns the starting year
     * a person participated in a show
     *
     * @return
     */
    public int getStartYear() {
        return startYear;
    }

    /**
     * Returns the final year
     * a person participated in a show
     *
     * @return
     */
    public int getEndYear() {
        return endYear;
    }

    /**
     * Returns the character name
     * being credited for
     *
     * @return
     */
    public String getCharacterName() {
        return characterName;
    }
}
