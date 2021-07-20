package moviemad.dao.implementation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import moviemad.dao.MakeConnection;
import moviemad.dao.interfaces.CreditRollDao;
import moviemad.model.CreditRoll;


public class CreditRollImpl implements CreditRollDao {
	private MakeConnection conn;
	
	public CreditRollImpl() {
		this.conn = new MakeConnection();
		
	}

	public Iterable<CreditRoll> getCreditByShowId(int showID) throws SQLException{
		ArrayList<CreditRoll> creditRolls = new ArrayList<>();
		String getCredits = String.format("SELECT * FROM "+ conn.getDBName()+".credits_roll WHERE show_id = " + showID);
		ResultSet rs = conn.createStatement().executeQuery(getCredits);	
		while (rs.next()) {
			creditRolls.add(new CreditRoll(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getString(6)));
		}
		return creditRolls;
		
	}

	public Iterable<CreditRoll> getCreditByPersonId(int personID) throws SQLException{
		ArrayList<CreditRoll> creditRolls = new ArrayList<>();
		String getCredits = String.format("SELECT * FROM credits_roll WHERE person_id = %d;", personID);
		ResultSet rs = conn.createStatement().executeQuery(getCredits);
		while (rs.next()) {
			creditRolls.add(new CreditRoll(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getString(6)));
		}
		return creditRolls;
	}
}
