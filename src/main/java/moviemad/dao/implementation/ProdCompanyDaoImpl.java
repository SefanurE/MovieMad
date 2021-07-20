
package moviemad.dao.implementation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import moviemad.dao.MakeConnection;
import moviemad.dao.interfaces.ProdCompanyDao;
import moviemad.model.ProdCompany;


public class ProdCompanyDaoImpl implements ProdCompanyDao{

		private MakeConnection conn;

		public ProdCompanyDaoImpl() {
			this.conn = new MakeConnection();
		}

		// Returns the Production Company Name based on the id passed
		public String getProdCompanybyProdId(int prodId) throws SQLException{
			String getCredits = String.format("SELECT * FROM "+ conn.getDBName()+".production_company WHERE proco_id= " + prodId);
			ResultSet rs = conn.createStatement().executeQuery(getCredits);
			String prodCompany = "";
			while (rs.next()) {
				 prodCompany = rs.getString(2);
			}
			return prodCompany;
		}

		// Returns a ProductionCompany based on the name passed
		public ProdCompany getProdCompanyByName(String name) throws SQLException{
			String queryID = String.format("SELECT * FROM production_company WHERE proco_name = '%s';", name);
			ResultSet rs = conn.createStatement().executeQuery(queryID);
			ProdCompany prodCompany = null;
			while(rs.next()){
				prodCompany = new ProdCompany(rs.getInt("proco_id"), rs.getString("proco_name"));
			}
			return prodCompany;
		}

		// Returns all Production Company names from the database
		public ArrayList<String> getAllProdCoNames() throws SQLException {
			ResultSet rs = conn.createStatement().executeQuery("SELECT proco_name FROM "+ conn.getDBName()+".production_company");
			ArrayList<String> pcoNames = new ArrayList<>();
			while(rs.next()){
				pcoNames.add(rs.getString("proco_name"));
			}
			return pcoNames;
		}
}

