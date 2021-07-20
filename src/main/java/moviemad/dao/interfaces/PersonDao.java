package moviemad.dao.interfaces;

import moviemad.model.Person;

import java.sql.SQLException;
import java.util.List;

public interface PersonDao {
    public List<Person> getPersonWhereNameContainsSubstring(String searchQueryOptimised) throws SQLException;

    public Iterable<Person> getPersonByShowId(int personID) throws SQLException;

    public Person getPersonById(int parseInt) throws SQLException;
}
