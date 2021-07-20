package moviemad.controller.login;

import static moviemad.utils.RequestUtil.getQueryPassword;
import static moviemad.utils.RequestUtil.getQueryUsername;
import static moviemad.utils.RequestUtil.removeSessionAttrLoggedOut;
import static moviemad.utils.RequestUtil.removeSessionAttrLoginRedirect;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import io.javalin.http.Handler;
import moviemad.dao.implementation.UserDaoImpl;
import moviemad.dao.interfaces.UserDao;
import moviemad.model.User;
import moviemad.paths.Template;
import moviemad.paths.Web;

public class LoginController {

	private static UserDao userDao = new UserDaoImpl();

	/**
	 * LoginController handles user login.
	 */

	/**
	 * Loads the login page into view.
	 */
	public static Handler login = ctx -> {
		Map<String, Object> model = new HashMap<>();
		model.put("loggedOut", removeSessionAttrLoggedOut(ctx));
		model.put("loginRedirect", removeSessionAttrLoginRedirect(ctx));
		if(ctx.cookieStore("registrationSuccessful")!= null){
			model.put("registrationSuccessful", true);
			ctx.clearCookieStore();
		}
		ctx.render(Template.LOGIN_PAGE, model);
	};

	/**
	 * Handles user login request.
	 */
	public static Handler processLogin = ctx -> {
		Map<String, Object> model = new HashMap<>();
		User user = null;
		try {
			user = userDao.getUserByUsernameAndPass(getQueryUsername(ctx), getQueryPassword(ctx));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (user == null) {
			model.put("userNotFound", true);
			ctx.render(Template.LOGIN_PAGE, model);
		} else if (!getQueryPassword(ctx).equals(user.getPassword())) {
			model.put("wrongPassword", true);
			ctx.render(Template.LOGIN_PAGE, model);
		} else if (user.getAccountStatus().equals("pending")){
			model.put("accountPending", true);
			ctx.render(Template.LOGIN_PAGE, model);
		} else {
			ctx.sessionAttribute("userType", user.getAccountType());
			ctx.sessionAttribute("currentUser", user.getUsername());
			ctx.redirect(Web.HOME);
		}
	};

	/**
	 * Handles logout.
	 */
	public static Handler logout = ctx -> {
		ctx.sessionAttribute("currentUser", null);
		ctx.sessionAttribute("userType", null);
		ctx.sessionAttribute("loggedOut", "true");
		ctx.redirect(Web.HOME);
	};

	public static Handler ensureLogin = ctx -> {
		if (!ctx.path().startsWith("/accountRequest") && !ctx.path().startsWith("/showRequest")) {
			return;
		}
		if (ctx.sessionAttribute("currentUser") == null) {
			ctx.sessionAttribute("loginRedirect", ctx.path());
			ctx.redirect(Web.LOGIN);
		} else if (!ctx.sessionAttribute("userType").equals("admin")) {
			ctx.sessionAttribute("loginRedirect", ctx.path());
			ctx.redirect(Web.LOGIN);
		}
	};
}
