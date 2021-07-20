package moviemad.controller.register;

import java.util.HashMap;
import java.util.Map;

import io.javalin.http.Handler;
import moviemad.paths.Template;

public class BasicUserRegistrationController extends RegistrationController {

	/**
	 * Loads basic registration page
	 */
	public static Handler serveBasicUserRegistrationPage = ctx -> {
		Map<String, Object> model = new HashMap<>();
		model.put("userType", "basicUser");
		ctx.render(Template.REGISTRATION, model);
	};
}