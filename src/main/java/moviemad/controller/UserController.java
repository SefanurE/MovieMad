package moviemad.controller;

import moviemad.dao.implementation.UserDaoImpl;
import moviemad.dao.interfaces.UserDao;
import moviemad.model.User;
import moviemad.utils.RequestUtil;

public class UserController {
	public static UserDao userDao = new UserDaoImpl();

	/**
	 * Authenticate the user by confirming if the username exists in the database
	 * and checks if the password entered, matches with the password in the database
	 * for that user.
	 */
	public static boolean authenticate(String username, String password) {
		boolean validLogin = false;
		if (username != null && password != null) {
			User user = userDao.getUserByUsername(username);
			if (user != null) {
				validLogin = password.equals(user.getPassword());
			}
		}
		return validLogin;
	}

	/**
	 * Returns true if the username is not in the database. Otherwise it returns
	 * false
	 */
	public static boolean checkUsernameUnique(String username) {
		boolean isUniqueUsername = false;
		if (username != null && !RequestUtil.isStringEmpty(username)) {
			isUniqueUsername = !userDao.usernameInDB(username);
		}
		return isUniqueUsername;
	}

	/**
	 * Returns true if the email is not in the database. Otherwise it returns false
	 */
	public static boolean checkEmailUnique(String email) {
		boolean isUniqueEmail = false;
		if (email != null && !RequestUtil.isStringEmpty(email))
			isUniqueEmail = !userDao.emailInDB(email);
		return isUniqueEmail;
	}

}
