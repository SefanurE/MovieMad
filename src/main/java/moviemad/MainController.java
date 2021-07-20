package moviemad;

import static io.javalin.apibuilder.ApiBuilder.before;
import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.post;

import io.javalin.Javalin;
import io.javalin.core.util.RouteOverviewPlugin;
import moviemad.controller.HomeController;
import moviemad.controller.PersonController;
import moviemad.controller.ShowController;
import moviemad.controller.accountRequest.AccountRequestController;
import moviemad.controller.login.LoginController;
import moviemad.controller.ratingsAndReviews.ReviewFormController;
import moviemad.controller.register.BasicUserRegistrationController;
import moviemad.controller.register.CriticRegistrationController;
import moviemad.controller.register.MainRegistrationController;
import moviemad.controller.register.PcoRegistrationController;
import moviemad.controller.search.SearchController;
import moviemad.controller.show.ShowEditController;
import moviemad.controller.show.ShowEntryController;
import moviemad.controller.showRequest.ShowRequestController;
import moviemad.dao.implementation.ShowDaoImpl;
import moviemad.dao.implementation.UserDaoImpl;
import moviemad.paths.Web;
import moviemad.utils.ViewUtil;

public class MainController {

	public static UserDaoImpl userDao;
	public static ShowDaoImpl showDao;

	public static void main(String[] args) {

		// Instantiate dependencies
		showDao = new ShowDaoImpl();
		userDao = new UserDaoImpl();

		Javalin app = Javalin.create(config -> {
			config.addStaticFiles("public");
			config.registerPlugin(new RouteOverviewPlugin("/routes"));
		}).start(8080);

		/**
		 * Add get(), post(), before(), after() route mapping Example: get(Web.INDEX,
		 * IndexController.serveIndexPage);
		 */
		app.routes(() -> {
			before(LoginController.ensureLogin);
			get("", HomeController.loadHomePage);
			get(Web.HOME, HomeController.loadHomePage);

			/** Login Routes */
			get(Web.LOGIN, LoginController.login);
			post(Web.LOGIN, LoginController.processLogin);
			get(Web.LOGOUT, LoginController.logout);

			/** Account Request */
			get(Web.ACCOUNT_REQUEST, AccountRequestController.loadRequests);
			post(Web.ACCOUNT_REQUEST, AccountRequestController.processRequest);

			/** Show Request */
			get(Web.SHOW_REQUEST, ShowRequestController.loadRequests);
			post(Web.SHOW_REQUEST, ShowRequestController.processRequest);

			/** Registration Routes */
			get(Web.MAINREGISTRATION, MainRegistrationController.serveMainRegistrationPage);
			get(Web.BASICUSERREGISTER, BasicUserRegistrationController.serveBasicUserRegistrationPage);
			get(Web.CRITICREGISTER, CriticRegistrationController.serveCriticRegistrationPage);
			get(Web.PCOREGISTER, PcoRegistrationController.servePcoRegistrationPage);
			post(Web.BASICUSERREGISTER, BasicUserRegistrationController.handleRegistrationPost);
			post(Web.PCOREGISTER, CriticRegistrationController.handleRegistrationPost);
			post(Web.CRITICREGISTER, PcoRegistrationController.handleRegistrationPost);

			/** Rating and Review Routes */
			get(Web.REVIEWFORM, ReviewFormController.serveReviewForm);
			post(Web.REVIEWFORM, ReviewFormController.processReviewForm);

			/** Show Routes */
			get(Web.SHOW, ShowController.fetchShow);
			get(Web.SHOWENTRY, ShowEntryController.serveShowEntry);
			post(Web.SHOWENTRY, ShowEntryController.handleShowEntryPost);
			post(Web.SHOWEDIT, ShowEditController.serveShowEdit);
			post(Web.SHOWEDITPOST, ShowEditController.handleShowEditPost);
			post(Web.SHOWEDITSUSPEND, ShowEditController.handleShowEditSuspend);
			post(Web.SHOWEDITDELETE, ShowEditController.handleShowEditDelete);




			/** Search Routes */
			get(Web.SEARCH, SearchController.loadSearchResults);

			/** Person Routes */
			get(Web.PERSON, PersonController.fetchPerson);
		});

		app.error(404, ViewUtil.notFound);
	}
}
