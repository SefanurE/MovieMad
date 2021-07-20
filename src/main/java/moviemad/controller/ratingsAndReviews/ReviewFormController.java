package moviemad.controller.ratingsAndReviews;

import io.javalin.http.Handler;
import moviemad.dao.implementation.ReviewDaoImpl;
import moviemad.dao.implementation.ShowDaoImpl;
import moviemad.dao.interfaces.ReviewDao;
import moviemad.dao.interfaces.ShowDao;
import moviemad.paths.Template;
import moviemad.paths.Web;
import moviemad.utils.ViewUtil;

import java.time.LocalDateTime;
import java.util.Map;

import static moviemad.utils.RequestUtil.getParamShow;

public class ReviewFormController {
    private static ReviewDao reviewDao = new ReviewDaoImpl();
    private static ShowDao showDao = new ShowDaoImpl();

    /**
     * Loads Review page
     */
    public static Handler serveReviewForm = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);

        // Redirects user to login page if not logged in an account
        if (ctx.sessionAttribute("currentUser") == null) {
            ctx.redirect(Web.LOGIN);
        } else {
            // Gets showID
            int showID = Integer.parseInt(getParamShow(ctx));

            // Put show information into model
            model.put("show",showDao.getShowByShowID(showID));

            // Render review page
            ctx.render(Template.REVIEW_FORM, model);
        }

    };

    /**
     * Processes the review form
     */
    public static Handler processReviewForm = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);

        // Retrieves showID which the review is for
        int showID = Integer.parseInt(getParamShow(ctx));

        // Retrieves the user Id of the user who keyed the review
        String userID = ctx.sessionAttribute("currentUser");

        // Retrieves the rating given by the reviewer
        int rating = Integer.parseInt(ctx.formParam("rating"));

        // Retrieves the review statement and adds external link to the end if the user is a pco or a critic
        String reviewStatements = null;
        if(ctx.formParam("link") != null){
            if(ctx.formParam("review").trim().equals("") || ctx.formParam("link").trim().equals("")){
                model.put("show",showDao.getShowByShowID(showID));
                model.put("emptyFieldError", true);
                ctx.render(Template.REVIEW_FORM, model);
            } else {
                reviewStatements = String.format("%s\n%s",ctx.formParam("review"), ctx.formParam("link"));
            }
        } else {
            if(ctx.formParam("review").trim().equals("")){
                model.put("show",showDao.getShowByShowID(showID));
                model.put("emptyFieldError", true);
                ctx.render(Template.REVIEW_FORM, model);
            } else {
                reviewStatements = String.format("%s",ctx.formParam("review"));
            }
        }

        if(reviewStatements != null){
            // Retrieves the current time and date
            String date = LocalDateTime.now().toString();

            // Requests the dao to add the review into the database
            reviewDao.insertUserReviewToDB(showID,userID,rating,reviewStatements, date);

            String showPage = String.format("/show/%s", showID);
            ctx.redirect(showPage);
        }
    };
}
