package moviemad.utils;

import io.javalin.http.Context;

public class RequestUtil {
	public static String getQueryUsername(Context ctx) {
		return ctx.formParam("username");
	}

	public static String getQueryPassword(Context ctx) {
		return ctx.formParam("password");
	}

	public static String getQueryLoginRedirect(Context ctx) {
		return ctx.queryParam("loginRedirect");
	}

	public static String getSessionCurrentUser(Context ctx) {
		return (String) ctx.sessionAttribute("currentUser");
	}

	public static boolean removeSessionAttrLoggedOut(Context ctx) {
		String loggedOut = ctx.sessionAttribute("loggedOut");
		ctx.sessionAttribute("loggedOut", null);
		return loggedOut != null;
	}

	public static String removeSessionAttrLoginRedirect(Context ctx) {
		String loginRedirect = ctx.sessionAttribute("loginRedirect");
		ctx.sessionAttribute("loginRedirect", null);
		return loginRedirect;
	}
	 public static String getParamShow(Context ctx) {
	        return ctx.pathParam("showId");
	}

	public static String getParamPerson(Context ctx) {
		return ctx.pathParam("personId");
	}

	public static boolean isStringEmpty(String string) {
		if (string == null) {
			return false;
		}
		return string.trim().equals("");
	}


}