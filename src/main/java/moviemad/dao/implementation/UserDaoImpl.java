package moviemad.dao.implementation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import moviemad.dao.MakeConnection;
import moviemad.dao.interfaces.UserDao;
import moviemad.model.Critic;
import moviemad.model.Pco;
import moviemad.model.User;
import moviemad.utils.UserTypeEnum;

public class UserDaoImpl implements UserDao {

	private static MakeConnection conn = new MakeConnection();

	/**
	 * Returns all the usernames in the database as an Iterable
	 */
	public Iterable<String> getAllUserNames() throws SQLException {
		String retrieveAllUserName = String.format("SELECT username FROM account;");
		ResultSet rs = conn.createStatement().executeQuery(retrieveAllUserName);
		ArrayList<String> allUsernames = new ArrayList<>();
		while (rs.next()) {
			allUsernames.add(rs.getString("username"));
		}
		return allUsernames;
	}

	/**
	 * Returns all the user emails in the database as an Iterable
	 */
	public Iterable<String> getAllEmails() throws SQLException {
		String retrieveAllEmails = String.format("SELECT email FROM account;");
		ResultSet rs = conn.createStatement().executeQuery(retrieveAllEmails);
		ArrayList<String> allEmails = new ArrayList<>();
		while (rs.next()) {
			allEmails.add(rs.getString("email"));
		}
		return allEmails;
	}

	public List<User> getUserByFilter(String query) throws SQLException {
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		List<User> tableData = new ArrayList<>();
		while (rs.next()) {
			User user = null;
			if (rs.getString("account_type").equals("pco")) {
				user = new Pco(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9),
						UserTypeEnum.getFormattedUserType(UserTypeEnum.pco), rs.getString(12), rs.getString(11),
						rs.getInt(1));
			} else if (rs.getString("account_type").equals("critic")) {
				user = new Critic(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8),
						UserTypeEnum.getFormattedUserType(UserTypeEnum.critic), rs.getString(12), rs.getString(11));
			}
			tableData.add(user);
		}
		return tableData;
	}

	/**
	 * Adds the basic User account details to the database
	 */
	public void addBasicUserToDB(String username, String password, String email, String country, String gender,
			String firstName, String lastName) throws SQLException {
		String insertNewBasicUser = String.format(
				"INSERT INTO `account` VALUES ('%s','%s','%s','%s','%s','%s','%s','approved','basic',null);",
				username, password, email, country, gender, firstName, lastName);

		conn.createStatement().executeUpdate(insertNewBasicUser);
	}

	/**
	 * Adds the Critic account details to the database
	 */
	public void addCriticUserToDB(String username, String password, String email, String country, String gender,
			String firstName, String lastName, String accountStatus, String accountType, String phoneNum,
			String companyName) throws SQLException {

		String insertNewCriticUser = String.format(
				"INSERT INTO `account` VALUES ('%s','%s','%s','%s','%s','%s','%s','%s','%s','null');", username,
				password, email, country, gender, firstName, lastName, accountStatus, accountType);
		String insertCriticUser = String.format("INSERT INTO `critic` VALUES ('%s','%s','%s')", username,companyName,phoneNum);
		conn.createStatement().executeUpdate(insertNewCriticUser);
		conn.createStatement().executeUpdate(insertCriticUser);
	}

	/**
	 * Adds the Production company account details to the database
	 */
	public void addPCOUserToDB(String username, String password, String email, String country, String gender,
			String firstName, String lastName, String accountStatus, String accountType, String phoneNum,
			String companyName) throws SQLException {
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT MAX(proco_id) FROM production_company");
		int proid = 0;
		while (rs.next()) {
		proid = rs.getInt(1);
		}
		String insertNewPcoUser = String.format(
				"INSERT INTO `account` VALUES ('%s','%s','%s','%s','%s','%s','%s','%s','%s',%d);", username,
				password, email, country, gender, firstName, lastName, accountStatus, accountType, proid+1);
		String insertPcoUser = String.format(
				"INSERT INTO `production_company` VALUES ('%d','%s','%s');",proid+1,companyName,phoneNum);

		conn.createStatement().executeUpdate(insertNewPcoUser);
		conn.createStatement().executeUpdate(insertPcoUser);
	}

	/**
	 * Returns true if the username already exists in the database, otherwise false
	 */
	public boolean usernameInDB(String username) {
		boolean inDB = false;
		try {
			if (username != null) {
					String queryUsernameCount = String.format("SELECT COUNT(username) FROM account WHERE username= '%s';",
							username);
					ResultSet rs = conn.createStatement().executeQuery(queryUsernameCount);
					int usernameCount = 0;
					while (rs.next()) {
						usernameCount = rs.getInt("COUNT(username)");
					}
					if (usernameCount >= 1)
						inDB = true;
					}
			return inDB;
		} catch (SQLException e) {
			return false;
		}
	}

	// returns true if the email already exists in the database, otherwise false
	public boolean emailInDB(String email) {
		boolean inDB = false;
		try {
			if (email != null) {
				String queryEmailCount = String.format("SELECT COUNT(email) FROM account WHERE email= '%s';", email);
				ResultSet rs = conn.createStatement().executeQuery(queryEmailCount);
				int emailCount = 0;
				while (rs.next()) {
					emailCount = rs.getInt("COUNT(email)");
				}
				if (emailCount >= 1) {
					inDB = true;
				}
			}	
			return inDB;
		} catch (SQLException e) {
			return false;
		}
		
	}

	public User getUserByUsername(String username) {
		String query = String.format("SELECT * FROM account WHERE username = \"%s\";", username);

		Statement stmt = conn.createStatement();
		Statement stmt2 = conn.createStatement();
		try {

		ResultSet rs = stmt.executeQuery(query);
		User user = null;
		while (rs.next()) {
			if (rs.getString("account_type").equals("admin")) {
				user = new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9));
			}
			if (rs.getString("account_type").equals("basic")) {
				user = new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9));

			} else if (rs.getString("account_type").equals("pco")) {
				String queryPco = String.format("SELECT * FROM production_company WHERE proco_id = \"%s\";", rs.getString(10));
				ResultSet pcors = stmt2.executeQuery(queryPco);
				while (pcors.next()) {
				user = new Pco(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9),
						pcors.getString(3),pcors.getString(2),rs.getInt(10));
				}
			} else if (rs.getString("account_type").equals("critic")) {
				String queryCritic = String.format("SELECT * FROM critic WHERE username = \"%s\";", username);
				ResultSet criticrs = stmt2.executeQuery(queryCritic);
				while (criticrs.next()) {
				user = new Critic(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9),
						criticrs.getString(2),criticrs.getString(3));
				}
			}
		}
		return user;
		} catch (SQLException e) {
			return null;
		}
	}

	public boolean updateUser(String query) throws SQLException {
		Statement stmt = conn.createStatement();
		int result = stmt.executeUpdate(query);
		if (result == 1) {
			return true;
		}
		return false;
	}

	public Pco getPcoByUsername(String username) throws SQLException {
		String queryPco = String.format("SELECT * FROM account NATURAL JOIN production_company WHERE username = '%s';", username);
		Pco user = null;
		ResultSet rs = conn.createStatement().executeQuery(queryPco);
		while (rs.next()) {
			user = new Pco(rs.getString("username"), rs.getString("password"), rs.getString("email"),
					rs.getString("country"), rs.getString("gender"), rs.getString("first_name"),
					rs.getString("last_name"), rs.getString("account_status"), rs.getString("account_type"),
					rs.getString("proco_name"), rs.getString("phone_number"), rs.getInt("proco_id"));
		}
		return user;
	}

	public User getUserByUsernameAndPass(String username, String password) throws SQLException {
		String query = String.format("SELECT * FROM account WHERE username = '%s' AND password = '%s';", username, password);
		ResultSet rs = conn.createStatement().executeQuery(query);
		User user = null;
		while (rs.next()){
			user = new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
					rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9));
		}
		return user;
	}
}
