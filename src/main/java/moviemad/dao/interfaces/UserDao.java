package moviemad.dao.interfaces;

import java.sql.SQLException;
import java.util.List;

import moviemad.model.Pco;
import moviemad.model.User;

public interface UserDao {

	public Iterable<String> getAllUserNames() throws SQLException;
	
	public Iterable<String> getAllEmails() throws SQLException;
	
	public List<User> getUserByFilter(String query) throws SQLException;
	
	public void addBasicUserToDB(String username, String password, String email, String country, String gender,
			String firstName, String lastName) throws SQLException;
	
	public void addCriticUserToDB(String username, String password, String email, String country, String gender,
			String firstName, String lastName, String accountStatus, String accountType, String phoneNum,
			String companyName) throws SQLException;
	
	public void addPCOUserToDB(String username, String password, String email, String country, String gender,
			String firstName, String lastName, String accountStatus, String accountType, String phoneNum,
			String companyName) throws SQLException;
	
	public boolean usernameInDB(String username);
	
	public boolean emailInDB(String email);
	
	public User getUserByUsername(String username);
	
	public boolean updateUser(String query) throws SQLException;

	public Pco getPcoByUsername(String username) throws SQLException;

	public User getUserByUsernameAndPass(String username, String password) throws SQLException;
}
