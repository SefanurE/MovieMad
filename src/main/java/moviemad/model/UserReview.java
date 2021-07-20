package moviemad.model;

import java.util.Date;

public class UserReview {
    private int reviewID;
    private int showID;
    private String userID;
    private int rating;
    private String review;
    private Date publicationDate;
    private String byUserType;
    private String accountStatus;

    /**
     * Constructs a User Review
     * @param reviewID
     * @param showID
     * @param userID
     * @param rating
     * @param review
     * @param publicationDate
     * @param byUserType
     * @param  accountStatus
     */
    public UserReview(int reviewID, int showID, String userID, int rating, String review, Date publicationDate,
                      String byUserType, String accountStatus) {
        this.reviewID = reviewID;
        this.showID = showID;
        this.userID = userID;
        this.rating = rating;
        this.review = review;
        this.publicationDate = publicationDate;
        this.byUserType = byUserType.toUpperCase();
        this.accountStatus = accountStatus;
    }

    /**
     * Returns the ID of the review
     *
     * @return
     */
    public int getReviewID() {
        return reviewID;
    }

    /**
     * Returns the ID of the show
     * the review is for
     *
     * @return
     */
    public int getShowID() {
        return showID;
    }

    /**
     * Returns the ID of the user
     * who submitted the review
     *
     * @return
     */
    public String getUserID() {
        return userID;
    }

    /**
     * Returns the rating given in the review
     *
     * @return
     */
    public int getRating() {
        return rating;
    }

    /**
     * Returns the user statements in the review
     *
     * @return
     */
    public String getReview() {
        return review;
    }

    /**
     * Returns the date the review was published
     *
     * @return
     */
    public Date getPublicationDate() {
        return publicationDate;
    }

    /**
     * Returns the user type of the account that created the review
     *
     * @return
     */
    public String getByUserType() {
        return byUserType;
    }

    /**
     * Returns the status of the account that created the review
     *
     * @return
     */
    public String getAccountStatus() {
        return accountStatus;
    }
}
