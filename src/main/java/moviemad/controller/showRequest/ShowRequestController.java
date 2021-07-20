package moviemad.controller.showRequest;

import java.util.List;
import java.util.Map;

import io.javalin.http.Handler;
import moviemad.dao.MakeConnection;
import moviemad.dao.implementation.ShowDaoImpl;
import moviemad.dao.interfaces.ShowDao;
import moviemad.model.Show;
import moviemad.paths.Template;
import moviemad.utils.ViewUtil;

public class ShowRequestController {
	private static ShowDao showDao = new ShowDaoImpl();

	/**
	 * Approval and rejection of account requests.
	 */
	public static Handler processRequest = ctx -> {
		Map<String, Object> model = ViewUtil.baseModel(ctx);
		String action = ctx.formParam("action");
		String showID = ctx.formParam("showID");
		boolean result = false;
		if ("accept".equals(action)) {
			String query = "UPDATE " + MakeConnection.getDBName() + ".show SET status = 'approved' WHERE showid = '"
					+ showID + "'";
			result = showDao.updateShow(query);
		} else if ("decline".equals(action)) {
			String query = "DELETE FROM " + MakeConnection.getDBName() + ".show WHERE showid = '" + showID + "'";
			result = showDao.updateShow(query);
		}
		if (result) {
			model.put("message", "Success");
		} else {
			model.put("error", "Operation failed");
		}
		List<Show> tableData = showDao.getUnapprovedShows();
		model.put("tableData", tableData);
		model.put("size", tableData.size());
		ctx.render(Template.SHOW_REQUEST, model);
	};

	/**
	 * Loads the pending requests into view
	 */
	public static Handler loadRequests = ctx -> {
		Map<String, Object> model = ViewUtil.baseModel(ctx);
		List<Show> tableData = showDao.getUnapprovedShows();
		model.put("size", tableData.size());
		model.put("tableData", tableData);
		ctx.render(Template.SHOW_REQUEST, model);
	};

}
