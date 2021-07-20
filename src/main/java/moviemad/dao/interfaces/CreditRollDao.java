package moviemad.dao.interfaces;

import java.sql.SQLException;

import moviemad.model.CreditRoll;


public interface CreditRollDao {
	public Iterable<CreditRoll> getCreditByShowId(int showID) throws SQLException;

    public Iterable<CreditRoll> getCreditByPersonId(int personID) throws SQLException;
}


