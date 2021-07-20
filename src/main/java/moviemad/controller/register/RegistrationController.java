package moviemad.controller.register;

import java.util.HashMap;
import java.util.Map;

import io.javalin.http.Handler;
import moviemad.controller.UserController;
import moviemad.dao.implementation.UserDaoImpl;
import moviemad.dao.interfaces.UserDao;
import moviemad.model.User;
import moviemad.paths.Template;
import moviemad.paths.Web;
import moviemad.utils.RequestUtil;
import moviemad.utils.ViewUtil;

public class RegistrationController {
	private static UserDao userDao = new UserDaoImpl();

	/**
	 * Handles all user types registration
	 */
	public static Handler handleRegistrationPost = ctx -> {
		String username = ctx.formParam("username");
		String password = ctx.formParam("password");
		String email = ctx.formParam("email").toLowerCase();
		String country = ctx.formParam("country");
		String gender = ctx.formParam("gender");
		String firstName = ctx.formParam("firstName");
		String lastName = ctx.formParam("lastName");
		String userType = ctx.formParam("userType");
		String phoneNo = "";
		String companyName = "";

		if(RequestUtil.isStringEmpty(username) || RequestUtil.isStringEmpty(password) || RequestUtil.isStringEmpty(email) ||
				RequestUtil.isStringEmpty(firstName) || RequestUtil.isStringEmpty(lastName)) {
			Map<String, Object> model = ViewUtil.baseModel(ctx);
			model.put("userType", userType);
			model.put("emptyFieldError", true);
			ctx.render(Template.REGISTRATION, model);
		} else {
			/**
			 * Includes the field to input the company name and phone number for a Critic or
			 * a Production Company registration.
			 */
			if (userType.equals("pco") || userType.equals("critic")) {
				companyName = ctx.formParam("companyName");
				phoneNo = ctx.formParam("phoneNo");
			}

			Map<String, Object> model = new HashMap<>();

			/**
			 * If user already exists an error message, stating the either the username or
			 * email already exists, will be rendered in the registration page.
			 */
			if (!UserController.checkUsernameUnique(username) || !UserController.checkEmailUnique(email)) {
				model.put("uniqueUserFailed", true);
				model.put("errorMessage", "Email or username already exists!");
				model.put("userType", userType);
				ctx.render(Template.REGISTRATION, model);
			} else {
				// Creates the user based on the user type from the registration page
				if (userType.equals("basicUser")) {
					userDao.addBasicUserToDB(username, password, email, country, gender, firstName, lastName);
				} else if (userType.equals("pco")) {
					userDao.addPCOUserToDB(username, password, email, country, gender, firstName, lastName, "pending",
							"pco", phoneNo, companyName);
				} else if (userType.equals("critic")) {
					userDao.addCriticUserToDB(username, password, email, country, gender, firstName, lastName, "pending",
							"critic", phoneNo, companyName);
				}

				User user = userDao.getUserByUsername(username);
				if(user == null){
					model.put("unsuccessfulRegistration", true);
					model.put("errorMessage", "Registration failed from a system error. Please try again later.");
					ctx.render(Template.REGISTRATION, model);
				} else{
					/**
					 * Stores that the registration was successful in a cookie
					 */
					ctx.cookieStore("registrationSuccessful", true);

					/**
					 * Redirects to the login page
					 */
					ctx.redirect(Web.LOGIN);
				}

			}
		}
	};

}
