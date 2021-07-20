package moviemad.dao.implementation;

import moviemad.dao.MakeConnection;
import moviemad.dao.interfaces.ReviewDao;
import moviemad.model.UserReview;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

public class ReviewDaoImpl implements ReviewDao {
    private static MakeConnection conn = new MakeConnection();

    // Returns an arraylist of Reviews based on the reviews stored in the database
    public ArrayList<UserReview> getAllReviews() throws SQLException {
        String selectAllReviews = String.format("SELECT user_review.*, account.account_type, account.account_status" +
                " FROM user_review LEFT JOIN account ON user_review.user_id=account.username;");
        ResultSet rs = conn.createStatement().executeQuery(selectAllReviews);
        ArrayList<UserReview> allReviews = new ArrayList<>();
        while(rs.next()){
            allReviews.add(new UserReview(rs.getInt(1), rs.getInt(2), rs.getString(3),
                    rs.getInt(4), rs.getString(5), rs.getDate(6),
                    rs.getString(7), rs.getString(8)));
        }

        return allReviews;
    }

    // Returns the average rating of a show
    public double getAverageRating(int showID) throws SQLException {
        String queryAverageRating = String.format("SELECT AVG(rating) FROM user_review WHERE show_id = %d;", showID);
        ResultSet rs = conn.createStatement().executeQuery(queryAverageRating);
        double average = 0.0;
        while (rs.next()) {
            average = rs.getDouble("AVG(rating)");
        }

        return average;
    }

    // Inserts a review into the database
    public void insertUserReviewToDB(int showID, String userID, int rating,
                                     String reviewStatements, String date) throws SQLException {
        String insertUserReview = String.format("INSERT INTO user_review VALUES(null, %d, '%s', %d, '%s', '%s');",
                showID, userID, rating, reviewStatements, date);

        conn.createStatement().executeUpdate(insertUserReview);
    }

    // Returns the count of reviews from the database
    public int getReviewCount() throws SQLException {
        String queryReviewCount = String.format("SELECT COUNT(reviewId) FROM user_review;");
        ResultSet rs = conn.createStatement().executeQuery(queryReviewCount);
        int reviewCount = 0;
        while(rs.next()){
            reviewCount = rs.getInt(1);
        }
        return reviewCount;
    }

    // Returns an array list of up to five show reviews in the database
    public ArrayList<UserReview> getFiveReviewsByShowID(int showID) throws SQLException {
        String selectShowReview = String.format("SELECT user_review.*, account.account_type, account.account_status" +
                " FROM user_review LEFT JOIN account ON user_review.user_id=account.username WHERE show_id = %d;", showID);
        ResultSet rs = conn.createStatement().executeQuery(selectShowReview);
        ArrayList<UserReview> reviews = new ArrayList<>();
        while (rs.next() && reviews.size() <= 5){
            reviews.add(new UserReview(rs.getInt(1), rs.getInt(2), rs.getString(3),
                    rs.getInt(4), rs.getString(5), rs.getDate(6),
                    rs.getString(7), rs.getString(8)));
        }
        return reviews;
    }

}