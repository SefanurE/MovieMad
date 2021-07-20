package moviemad.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TestQuery {

	/**
	 * Class to test database connection
	 */

	public static void main(String[] args) throws SQLException {
		MakeConnection conn = new MakeConnection();

		String retrieveAllShows = String.format("SELECT show_title FROM show;");
		ResultSet rs = conn.createStatement().executeQuery(retrieveAllShows);
		ArrayList<String> allShows = new ArrayList<>();
		while (rs.next()) {
			allShows.add(rs.getString("show_title"));
		}

	}
}
