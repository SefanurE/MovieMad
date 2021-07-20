package moviemad.controller.show;

import io.javalin.core.util.FileUtil;
import io.javalin.http.Handler;
import io.javalin.http.UploadedFile;
import moviemad.dao.MakeConnection;
import moviemad.dao.implementation.ProdCompanyDaoImpl;
import moviemad.dao.implementation.ShowDaoImpl;
import moviemad.dao.implementation.UserDaoImpl;
import moviemad.dao.interfaces.ProdCompanyDao;
import moviemad.dao.interfaces.ShowDao;
import moviemad.dao.interfaces.UserDao;
import moviemad.model.Pco;
import moviemad.model.ProdCompany;
import moviemad.model.User;
import moviemad.paths.Template;
import moviemad.paths.Web;
import moviemad.utils.EmailUtil;
import moviemad.utils.ViewUtil;

import org.apache.commons.mail.EmailException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class ShowEntryController {

    /**
     * ShowEntryController handles the show entry functionality, where registered
     * users can submit an entry for a show.
     */

    private static UserDao userDao = new UserDaoImpl();
    private static ShowDao showDao = new ShowDaoImpl();
    private static ProdCompanyDao prodCompanyDao = new ProdCompanyDaoImpl();

    /**
     * Serve show entry page
     */
    public static Handler serveShowEntry = ctx -> {

        // Redirects user to login page if not logged in an account
        if (ctx.sessionAttribute("currentUser") == null) {
            ctx.redirect(Web.LOGIN);
        } else {
            Map<String, Object> model = ViewUtil.baseModel(ctx);
            if(ctx.sessionAttribute("userType").equals("pco")){
                Pco pcoUser = (Pco) userDao.getUserByUsername(ctx.sessionAttribute("currentUser"));
                model.put("pcoName", pcoUser.getCompanyName());
            } else {
                ArrayList<String> pcoNameList = prodCompanyDao.getAllProdCoNames();
                model.put("pcoList", pcoNameList);
            }
            ctx.render(Template.SHOW_ENTRY, model);
        }
    };

    /**
     * Handles show entry submission, emailing user if necessary.
     */
    public static Handler handleShowEntryPost = ctx -> {

        if(ctx.formParam("title").trim().equals("") || ctx.formParam("genre").trim().equals("") ||
        ctx.formParam("description").trim().equals("")){
            Map<String, Object> model = ViewUtil.baseModel(ctx);
            if(ctx.sessionAttribute("userType").equals("pco")){
                Pco pcoUser = (Pco) userDao.getUserByUsername(ctx.sessionAttribute("currentUser"));
                model.put("pcoName", pcoUser.getCompanyName());
            } else {
                ArrayList<String> pcoNameList = prodCompanyDao.getAllProdCoNames();
                model.put("pcoList", pcoNameList);
            }
            model.put("emptyFieldError", true);
            ctx.render(Template.SHOW_ENTRY, model);
        } else {
            // Retrieves show type from form
            String type = ctx.formParam("type");

            // Retrieves title from form
            String title = ctx.formParam("title");

            // Retrieves genre from form
            String genre = ctx.formParam("genre");

            // Retrieves length from form
            double length = Double.parseDouble(ctx.formParam("length"));

            // Movie and series are given an integer value equivalent to boolean if their type matches
            int movie =0;
            int series =0;
            if(type.equals("movie")){
                movie = 1;
            } else if(type.equals("series")){
                series = 1;
            }

            // Retrieves year from form
            int year = Integer.parseInt(ctx.formParam("year"));

            // Retrieves description from form
            String description = ctx.formParam("description");

            //Adds file to images folder and sets the path with the file name
            UploadedFile file = ctx.uploadedFile("file");
            String savePath = "src/main/resources/public/images/" + file.getFilename();
            String imagePath = "/images/" + file.getFilename();
            FileUtil.streamToFile(file.getContent(), savePath);

            // Retrieves the Production Company from form
            String pco = ctx.formParam("pco");
            ProdCompany prodCompany = prodCompanyDao.getProdCompanyByName(pco);

            String userType = ctx.sessionAttribute("userType");
            try {
                if(userType.equals("pco")) {

                    // Adds the show to the database with a pending status
                    showDao.addShowToDB(title, genre, length, movie, series, prodCompany.getProdId(), year, description, imagePath,"pending", userType);
                    User pcoUser = userDao.getUserByUsername(ctx.sessionAttribute("currentUser"));

                    // Emails the PCo user about the show entry
                    EmailUtil.emailUser("You recently added a show to the movie mad database. It will be automatically approved in 24 hours.",
                            "SHOW ENTRY CONFIRMATION", pcoUser.getEmail());

                    // Creates a new thread which updates all PCo show entries to approved in 24 hours.
                    Runnable updateStatus = () -> {
                        try {
                            Thread.sleep(1000*60*60*24);
                            MakeConnection connection = new MakeConnection();
                            ArrayList<Integer> showIDs = showDao.getShowIDs("SELECT showid FROM moviemad.show WHERE user_type = 'pco';");
                            for(Integer id : showIDs){
                                String update = String.format("UPDATE moviemad.show SET status = 'approved' WHERE show.showid = %d AND show.user_type= 'pco';", id);
                                connection.createStatement().executeUpdate(update);
                            }
                        } catch (InterruptedException | SQLException e) {
                            e.printStackTrace();
                        }
                    };
                    new Thread(updateStatus).start();

                    // Redirects user back to the homepage
                    ctx.redirect(Web.HOME);
                }
                else if (userType.equals("admin")){
                    // Adds the show to the database with an approved status
                    showDao.addShowToDB(title, genre, length, movie, series, prodCompany.getProdId(), year, description, imagePath, "approved", userType);
                    ctx.redirect(Web.HOME);

                } else if (userType.equals("basic") || userType.equals("critic")){
                    // Adds the show to the database with a pending status
                    showDao.addShowToDB(title, genre, length, movie, series, prodCompany.getProdId(), year, description, imagePath, "pending", userType);
                    ctx.redirect(Web.HOME);

                }
            } catch(EmailException e) {
                System.out.println(e.toString());
                ctx.redirect(Web.HOME);

            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    };


}
