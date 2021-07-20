
package moviemad.dao.implementation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import moviemad.dao.MakeConnection;
import moviemad.dao.interfaces.ShowDao;
import moviemad.model.Show;

public class ShowDaoImpl implements ShowDao {
	private static MakeConnection conn = new MakeConnection();

	/**
	 * returns the list of shows in the database
	 * 
	 * @return
	 * @throws SQLException
	 */
	@Override
	public Iterable<String> getAllShowTitles() throws SQLException {
		String retrieveAllShows = String.format("SELECT show_title FROM " + MakeConnection.getDBName() + ".show;");
		ResultSet rs = conn.createStatement().executeQuery(retrieveAllShows);
		ArrayList<String> allShows = new ArrayList<>();
		while (rs.next()) {
			allShows.add(rs.getString("show_title"));
		}
		return allShows;
	}

	/**
	 * returns a show with the corresponding show ID
	 * 
	 * @param showID
	 * @return
	 * @throws SQLException
	 */
	@Override
	public Show getShowByShowID(int showID) throws SQLException {
		String getShow = String.format("SELECT " + conn.getDBName() + ".show.*, " + conn.getDBName()
				+ ".production_company.proco_name " + "FROM " + conn.getDBName() + ".show LEFT JOIN " + conn.getDBName()
				+ ".production_company " + "ON " + conn.getDBName()
				+ ".show.proco_id=production_company.proco_id WHERE show.showid = %d;", showID);
		ResultSet rs = conn.createStatement().executeQuery(getShow);
		Show show = null;
		while (rs.next()) {
			show = new Show(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getInt(5), rs.getInt(6),
					rs.getInt(7), rs.getInt(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12),
					rs.getString(13));
		}
		return show;
	}

	/**
	 * returns a random show retrieved from the database
	 * 
	 * @return
	 * @throws SQLException
	 */
	@Override
	public Show[] getRandomShows() throws SQLException {
		String getMaxID = String.format("SELECT MAX(showid) FROM " + conn.getDBName() + ".show");
		ResultSet rs = conn.createStatement().executeQuery(getMaxID);
		int max = 0;
		int[] rand = new int[3];
		while (rs.next()) {
			max = rs.getInt(1);
		}
		Show[] showArray = new Show[max];
		for (int i = 1; i <= max; i++) {
			Show show = this.getShowByShowID(i);
			showArray[i - 1] = show;
		}
		return showArray;
	}

	/**
	 * Adds a show to the database
	 *
	 * @param showTitle, genre, length, isMovie, isSeries, procoID, year,
	 *                   description, imagePath, status
	 * @throws SQLException
	 */
	@Override
	public void addShowToDB(String showTitle, String genre, double length, int isMovie, int isSeries, int procoID,
			int year, String description, String imagePath, String status, String submittedByUserType)
			throws SQLException {
		String insertShow = String.format(
				"INSERT INTO `show` VALUES (null, '%s', '%s', %.2f, %d, %d, %d, %d,'%s','%s','%s', '%s');", showTitle,
				genre, length, isMovie, isSeries, procoID, year, description, imagePath, status, submittedByUserType);

		conn.createStatement().executeUpdate(insertShow);
	}

	@Override
	public List<Show> getShowsByStatus(String status) throws SQLException {
		String getShow = String.format("SELECT " + conn.getDBName() + ".show.*, " + conn.getDBName()
				+ ".production_company.proco_name " + "FROM " + conn.getDBName() + ".show LEFT JOIN " + conn.getDBName()
				+ ".production_company " + "ON " + conn.getDBName()
				+ ".show.proco_id=production_company.proco_id WHERE show.status ='%s';", status);
		ResultSet rs = conn.createStatement().executeQuery(getShow);
		List<Show> shows = new ArrayList<>();
		while (rs.next()) {
			Show show = new Show(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getInt(5),
					rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getString(9), rs.getString(10), rs.getString(11),
					rs.getString(12), rs.getString(13));

			shows.add(show);
		}
		return shows;
	}

	@Override
	public List<Show> getUnapprovedShows() throws SQLException {
		String getShow = String.format("SELECT " + MakeConnection.getDBName()
				+ ".show.*, production_company.proco_name " + "FROM " + MakeConnection.getDBName()
				+ ".show LEFT JOIN production_company " + "ON " + MakeConnection.getDBName()
				+ ".show.proco_id=production_company.proco_id WHERE show.status ='pending' and show.user_type != 'pco';");
		ResultSet rs = conn.createStatement().executeQuery(getShow);
		List<Show> shows = new ArrayList<>();
		while (rs.next()) {
			Show show = new Show(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getInt(5),
					rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getString(9), rs.getString(10), rs.getString(11),
					rs.getString(12), rs.getString(13));

			shows.add(show);
		}

		shows.addAll(getShowsByStatus("suspended"));
		return shows;
	}

	@Override
	public List<Show> getShowsWhereNameContainsSubstring(String substring) throws SQLException {
		String getShow = String.format("SELECT " + conn.getDBName() + ".show.*, " + conn.getDBName()
				+ ".production_company.proco_name " + "FROM " + conn.getDBName() + ".show LEFT JOIN " + conn.getDBName()
				+ ".production_company " + "ON " + conn.getDBName()
				+ ".show.proco_id=production_company.proco_id WHERE LOWER(show.show_title) LIKE '%%%s%%'"
				+ "AND status = 'approved';", substring);
		ResultSet rs = conn.createStatement().executeQuery(getShow);
		List<Show> shows = new ArrayList<>();
		while (rs.next()) {
			Show show = new Show(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getInt(5),
					rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getString(9), rs.getString(10), rs.getString(11),
					rs.getString(12), rs.getString(13));

			shows.add(show);
		}
		return shows;

	}

	@Override
	public boolean updateShow(String query) throws SQLException {
		Statement stmt = conn.createStatement();
		int result = stmt.executeUpdate(query);
		if (result == 1) {
			return true;
		}
		return false;
	}

	@Override
	public ArrayList<Integer> getShowIDs(String query) throws SQLException {
		ArrayList<Integer> showIDs = new ArrayList<Integer>();
		ResultSet rs = conn.createStatement().executeQuery(query);
		while (rs.next()) {
			showIDs.add(rs.getInt(1));
		}
		return showIDs;
	}

	@Override
	public void modifyShowDB(String showTitle, String genre, double length, int isMovie, int isSeries, int procoID,
			int year, String description, String imagePath, String status, String submittedByUserType, int show_id)
			throws SQLException {

		String editShow = String.format("UPDATE "+ conn.getDBName() + ".show SET show_title = '%s',genre = '%s',length = '%.2f',movie = '%d',series = '%d', proco_id = '%d',year = '%s',description ='%s',image_path ='%s',status = '%s',user_type = '%s' WHERE showid = '%d'",
				showTitle, genre, length, isMovie, isSeries, procoID, year, description, imagePath, status, submittedByUserType, show_id);

		conn.createStatement().executeUpdate(editShow);
		}
	


	@Override
	public List<Show> getAllValidShows() throws SQLException {
		String retrieveAllShows = String.format("SELECT * FROM " + MakeConnection.getDBName() + ".show NATURAL JOIN "
				+ MakeConnection.getDBName() + ".production_company WHERE status = \"approved\"");
		ResultSet rs = conn.createStatement().executeQuery(retrieveAllShows);
		List<Show> shows = new ArrayList<>();
		while (rs.next()) {
			Show show = new Show(rs.getInt(2), rs.getString(3), rs.getString(4), rs.getDouble(5), rs.getInt(6),
					rs.getInt(7), rs.getInt(1), rs.getInt(8), rs.getString(9), rs.getString(10), rs.getString(11),
					rs.getString(12), rs.getString(13));
			shows.add(show);

		}
		return shows;
	}

	@Override

	public void suspendShow(int show_id) throws SQLException {
		String suspendShow = String
				.format("UPDATE " + conn.getDBName() + ".show  SET status = 'suspended' WHERE showid = '%d'", show_id);
		conn.createStatement().executeUpdate(suspendShow);

	}
	@Override
	public void deleteShow(int show_id) throws SQLException {
		String deleteReview = String.format("DELETE FROM " + conn.getDBName()+ ".user_review WHERE show_id = '%d'",show_id);
		String deleteShow = String.format("DELETE FROM " + conn.getDBName()+ ".show WHERE showid = '%d'",show_id);
		conn.createStatement().executeUpdate(deleteReview);
		conn.createStatement().executeUpdate(deleteShow);
		

	}

	@Override
	public List<Show> getAllShows() throws SQLException {
		String retrieveAllShows = String.format("SELECT * FROM " + MakeConnection.getDBName() + ".show NATURAL JOIN "
				+ MakeConnection.getDBName() + ".production_company");
		ResultSet rs = conn.createStatement().executeQuery(retrieveAllShows);
		List<Show> shows = new ArrayList<>();
		while (rs.next()) {
			Show show = new Show(rs.getInt(2), rs.getString(3), rs.getString(4), rs.getDouble(5), rs.getInt(6),
					rs.getInt(7), rs.getInt(1), rs.getInt(8), rs.getString(9), rs.getString(10), rs.getString(11),
					rs.getString(12), rs.getString(13));
			shows.add(show);
		}
		return shows;
	}

}