package moviemad.dao.interfaces;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import moviemad.model.Show;

public interface ShowDao {

	public Iterable<String> getAllShowTitles() throws SQLException;
	
	public Show getShowByShowID(int showID) throws SQLException;

	public List<Show> getShowsByStatus(String status) throws SQLException;

	public List<Show> getShowsWhereNameContainsSubstring(String substring) throws SQLException;
	
	public Show[] getRandomShows() throws SQLException;
	
	public boolean updateShow(String query) throws SQLException;

	public void addShowToDB(String showTitle, String genre, double length, int isMovie, int isSeries, int procoID,
							int year, String description, String imagePath, String status, String submittedByUserType) throws SQLException;


	
	public void modifyShowDB(String showTitle, String genre, double length, int isMovie, int isSeries, int procoID,
			int year, String description, String imagePath, String status, String submittedByUserType, int show_id)
			throws SQLException;
	
	public List<Show> getAllValidShows() throws SQLException;
	
	public void suspendShow(int show_id) throws SQLException;
	
	public void deleteShow(int show_id) throws SQLException;
	
	public List<Show> getAllShows() throws SQLException;
	
	public List<Show> getUnapprovedShows() throws SQLException;

	public ArrayList<Integer> getShowIDs(String query) throws SQLException;

}
	
