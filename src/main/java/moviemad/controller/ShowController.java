package moviemad.controller;

import java.util.ArrayList;
import java.util.Map;


import io.javalin.http.Handler;
import moviemad.dao.implementation.*;
import moviemad.dao.interfaces.*;
import moviemad.model.*;
import moviemad.paths.Template;
import moviemad.utils.ViewUtil;

import static moviemad.utils.RequestUtil.getParamShow;



public class ShowController {
	
	private static ShowDao showDao = new ShowDaoImpl();
	private static CreditRollDao creditDao = new CreditRollImpl();
	private static ProdCompanyDao prodDao = new ProdCompanyDaoImpl();
	private static PersonDao personDao = new PersonDaoImpl();
	private static ReviewDao reviewDao = new ReviewDaoImpl();
	private static UserDao userDao = new UserDaoImpl();

	  public static Handler fetchShow = ctx -> {
		  Map<String, Object> model = ViewUtil.baseModel(ctx);
		  int prodId = showDao.getShowByShowID(Integer.parseInt(getParamShow(ctx))).getProCoID();
	     model.put("show",showDao.getShowByShowID(Integer.parseInt(getParamShow(ctx))));

	      model.put("productionCompany", prodDao.getProdCompanybyProdId(prodId));
	     
	      ArrayList<CreditRoll> creditRolls = (ArrayList<CreditRoll>) creditDao.getCreditByShowId(Integer.parseInt(getParamShow(ctx)));
	      model.put("creditRoll",creditRolls);
	      ArrayList<Person> Person = new ArrayList<>();
	      for(int i = 0; i<creditRolls.size(); i++) {
	    	  Person.addAll((ArrayList<Person>) personDao.getPersonByShowId(creditRolls.get(i).getPersonID()));
	      }

	      // Adds the list of reviews to the model
		  ArrayList<UserReview> listOfReviews = reviewDao.getFiveReviewsByShowID(Integer.parseInt(getParamShow(ctx)));
		  model.put("reviewList", listOfReviews);

	      model.put("person", Person);

	      if(ctx.sessionAttribute("userType") != null){
	      	if (ctx.sessionAttribute("userType").equals("pco")){
				Pco pco = userDao.getPcoByUsername(ctx.sessionAttribute("currentUser"));
				model.put("pcoID", pco.getProdID());
			}
		  }

	      ctx.render(Template.SHOW, model);
	    };

}
