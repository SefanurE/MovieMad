package moviemad.controller.register;

import java.util.HashMap;
import java.util.Map;

import io.javalin.http.Handler;
import moviemad.paths.Template;

public class CriticRegistrationController extends RegistrationController {

	/**
	 * Loads Critic registration page
	 */
	public static Handler serveCriticRegistrationPage = ctx -> {
		Map<String, Object> model = new HashMap<>();
		model.put("userType", "critic");
		ctx.render(Template.REGISTRATION, model);
	};
}
