package moviemad.utils;

import java.util.HashMap;
import java.util.Map;

import io.javalin.http.Context;
import io.javalin.http.ErrorHandler;
import moviemad.paths.Template;

public class ViewUtil {

	public static Map<String, Object> baseModel(Context ctx) {
		Map<String, Object> model = new HashMap<>();
		model.put("currentUser", (String) ctx.sessionAttribute("currentUser"));
		model.put("userType", (String) ctx.sessionAttribute("userType"));
		if (ctx.sessionAttribute("currentUser") != null) {
			model.put("authenticationSucceeded", true);
		}
		return model;
	}

	public static ErrorHandler notFound = ctx -> {
		ctx.render(Template.NOT_FOUND, baseModel(ctx));
	};

}
