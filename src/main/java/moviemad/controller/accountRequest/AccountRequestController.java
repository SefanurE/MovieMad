package moviemad.controller.accountRequest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.javalin.http.Handler;
import moviemad.dao.implementation.UserDaoImpl;
import moviemad.dao.interfaces.UserDao;
import moviemad.model.User;
import moviemad.paths.Template;
import moviemad.utils.ViewUtil;

public class AccountRequestController {

	/**
	 * AccountRequestController handles the Administrator functionality of approving
	 * PCo and Critic requests.
	 */

	private static UserDao userDao = new UserDaoImpl();

	/**
	 * Approval and rejection of account requests.
	 */
	public static Handler processRequest = ctx -> {
		Map<String, Object> model = ViewUtil.baseModel(ctx);
		String action = ctx.formParam("action");
		String username = ctx.formParam("username");
		boolean result = false;
		if ("accept".equals(action)) {
			String query = "UPDATE account SET account_status = 'approved' WHERE username = '" + username + "'";
			result = userDao.updateUser(query);
		} else if ("decline".equals(action)) {
			String query = "DELETE FROM account WHERE username = '" + username + "'";
			result = userDao.updateUser(query);
		}
		if (result) {
			model.put("message", "Success");
		} else {
			model.put("error", "Operation failed");
		}
		List<User> tableData = getUserList();
		model.put("tableData", tableData);
		model.put("size", tableData.size());
		ctx.render(Template.ACCOUNT_REQUEST, model);
	};

	/**
	 * Loads the pending requests into view
	 */
	public static Handler loadRequests = ctx -> {
		Map<String, Object> model = ViewUtil.baseModel(ctx);
		List<User> tableData = getUserList();
		model.put("tableData", tableData);
		// model.put("tableData", tableDataCritic);
		model.put("size", tableData.size());

		ctx.render(Template.ACCOUNT_REQUEST, model);
	};

	/**
	 * Returns a list of users whose account status is pending.
	 * 
	 * @return List<User>
	 */
	private static List<User> getUserList() {
		String query = "SELECT * FROM account NATURAL JOIN production_company WHERE account_status = 'pending'";
		String query2 = "SELECT * FROM account NATURAL JOIN critic WHERE account_status = 'pending'";
		List<User> tableDataPco = new ArrayList<>();
		List<User> tableDataCritic = new ArrayList<>();
		try {
			tableDataPco = userDao.getUserByFilter(query);
			tableDataCritic = userDao.getUserByFilter(query2);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		// List<Object> tableData = new ArrayList<>();
		// tableData.add(tableDataPco);
		// tableData.add(tableDataCritic);
		List<User> tableData = tableDataPco;
		tableData.addAll(tableDataCritic);
		return tableData;
	}

}