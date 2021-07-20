package moviemad.controller;

import io.javalin.http.Handler;
import moviemad.dao.implementation.CreditRollImpl;
import moviemad.dao.implementation.PersonDaoImpl;
import moviemad.dao.implementation.ShowDaoImpl;
import moviemad.dao.interfaces.CreditRollDao;
import moviemad.dao.interfaces.PersonDao;
import moviemad.dao.interfaces.ShowDao;
import moviemad.model.CreditRoll;
import moviemad.model.Person;
import moviemad.model.Show;
import moviemad.paths.Template;
import moviemad.utils.ViewUtil;
import static moviemad.utils.RequestUtil.getParamPerson;

import java.util.ArrayList;
import java.util.Map;

public class PersonController {
    private static PersonDao personDao = new PersonDaoImpl();
    private static CreditRollDao creditRollDao = new CreditRollImpl();
    private static ShowDao showDao = new ShowDaoImpl();

    public static Handler fetchPerson = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        Person person = personDao.getPersonById(Integer.parseInt(getParamPerson(ctx)));
        Iterable<CreditRoll> creditRollList = creditRollDao.getCreditByPersonId(Integer.parseInt(getParamPerson(ctx)));
        ArrayList<Show> shows = new ArrayList<>();
        for(CreditRoll credit : creditRollList){
            Show show = showDao.getShowByShowID(credit.getShowID());
            shows.add(show);
        }

        model.put("credits", creditRollList);
        model.put("shows", shows);
        model.put("person", person);
        ctx.render(Template.PERSON, model);
    };
}
