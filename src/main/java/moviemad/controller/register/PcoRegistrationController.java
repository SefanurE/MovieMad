package moviemad.controller.register;

import java.util.HashMap;
import java.util.Map;

import io.javalin.http.Handler;
import moviemad.paths.Template;

public class PcoRegistrationController extends RegistrationController {

	/**
	 * Loads PCo registration page
	 */
	public static Handler servePcoRegistrationPage = ctx -> {
		Map<String, Object> model = new HashMap<>();
		model.put("userType", "pco");
		ctx.render(Template.REGISTRATION, model);
	};
}
