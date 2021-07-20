package moviemad.controller.search;

import io.javalin.http.Handler;
import moviemad.dao.implementation.PersonDaoImpl;
import moviemad.dao.implementation.ShowDaoImpl;
import moviemad.dao.interfaces.PersonDao;
import moviemad.dao.interfaces.ShowDao;
import moviemad.model.Person;
import moviemad.model.Show;
import moviemad.paths.Template;
import moviemad.utils.ViewUtil;
import org.apache.commons.lang3.StringEscapeUtils;

import java.util.*;

public class SearchController {
    private static ShowDao showDao = new ShowDaoImpl();
    private static PersonDao personDao = new PersonDaoImpl();

    /**
     * Loads Search result page, displaying results relevant to query
     */
    public static Handler loadSearchResults = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);

        // Get sanitized search query
        String searchQuery = ctx.queryParam("query");
        searchQuery = StringEscapeUtils.escapeHtml4(searchQuery).trim();
        String searchQueryOptimised = searchQuery.toLowerCase();
        model.put("searchQuery", searchQuery);

        // Get shows which contain search query
        List<Show> shows = showDao.getShowsWhereNameContainsSubstring(searchQueryOptimised);
        ArrayList<Show> movies = new ArrayList<Show>();
        ArrayList<Show> series = new ArrayList<Show>();

        // Get People which contain search query
        List<Person> people = personDao.getPersonWhereNameContainsSubstring(searchQueryOptimised);


        // Separate shows into movies and series
        if(shows.isEmpty() && people.isEmpty())
            model.put("noResults", true);
        else {
            for(Show show : shows){
                if(show.getType().equals("Series"))
                    series.add(show);
                else if(show.getType().equals("Movie"))
                    movies.add(show);
            }
            model.put("movies", movies);
            model.put("series", series);
        }

        model.put("people", people);

        ctx.render(Template.SEARCH_RESULTS, model);
    };



}
