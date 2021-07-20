package moviemad.controller;

import java.util.Map;

import io.javalin.http.Handler;
import moviemad.dao.interfaces.ShowDao;
import moviemad.paths.Template;
import moviemad.utils.ViewUtil;
import moviemad.dao.implementation.ShowDaoImpl;
import moviemad.model.Show;

public class HomeController {
	private static ShowDao showDao = new ShowDaoImpl();

	/**
	 * Loads home page
	 */
	public static Handler loadHomePage = ctx -> {
		Map<String, Object> model = ViewUtil.baseModel(ctx);

		Iterable<Show> shows = showDao.getAllValidShows();
		model.put("shows", shows);

		ctx.render(Template.HOME, model);
	};

}
