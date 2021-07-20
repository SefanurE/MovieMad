package moviemad.controller.register;

import java.util.HashMap;
import java.util.Map;

import io.javalin.http.Handler;
import moviemad.paths.Template;

public class MainRegistrationController {

	/**
	 * Loads main registration page
	 */
	public static Handler serveMainRegistrationPage = ctx -> {
		Map<String, Object> model = new HashMap<>();
		ctx.render(Template.MAIN_REGISTRATION, model);
	};
}
