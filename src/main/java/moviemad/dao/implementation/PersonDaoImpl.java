package moviemad.dao.implementation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import moviemad.dao.MakeConnection;
import moviemad.dao.interfaces.PersonDao;
import moviemad.model.Person;


public class PersonDaoImpl implements PersonDao {
	private MakeConnection conn;
	
	public PersonDaoImpl() {
		this.conn = new MakeConnection();
		
	}

	public Iterable<Person> getPersonByShowId(int personID) throws SQLException{
		ArrayList<Person> Person = new ArrayList<>();
		String getCredits = String.format("SELECT * FROM "+ conn.getDBName()+".person WHERE person_id = " + personID);
		ResultSet rs = conn.createStatement().executeQuery(getCredits);	
		while (rs.next()) {
			Person.add(new Person(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getString(5)));
		}
		return Person;
	}

	public Person getPersonById(int id) throws SQLException {
		Person person = null;
		String requestPerson = String.format("SELECT * FROM person WHERE person_id = %d;", id);
		ResultSet rs = conn.createStatement().executeQuery(requestPerson);
		while(rs.next()){
			person = new Person(rs.getInt(1), rs.getString(2), rs.getString(3),
					rs.getDate(4), rs.getString(5));
		}

		return person;
	}

	public List<Person> getPersonWhereNameContainsSubstring(String substring) throws SQLException {
		String getPerson = String.format("SELECT * FROM person WHERE LOWER(person.fullname) LIKE '%%%s%%';", substring);
		ResultSet rs = conn.createStatement().executeQuery(getPerson);
		List<Person> people = new ArrayList<>();
		while (rs.next()) {
			Person person = new Person(rs.getInt(1), rs.getString(2), rs.getString(3),
					rs.getDate(4), rs.getString(5));

			people.add(person);
		}
		return people;
	}


}
