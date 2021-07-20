package moviemad.dao.interfaces;

import moviemad.model.UserReview;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ReviewDao {
    public ArrayList<UserReview> getAllReviews() throws SQLException;

    public double getAverageRating(int showID) throws SQLException;

    public void insertUserReviewToDB(int showID, String userID, int rating,
                                     String reviewStatements, String date) throws SQLException;

    public int getReviewCount() throws SQLException;

    public ArrayList<UserReview> getFiveReviewsByShowID(int showID) throws SQLException;
}
