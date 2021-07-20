package moviemad.dao.interfaces;

import moviemad.model.ProdCompany;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ProdCompanyDao {
    public String getProdCompanybyProdId(int prodId) throws SQLException;

    public ProdCompany getProdCompanyByName(String name) throws SQLException;

    public ArrayList<String> getAllProdCoNames() throws SQLException;
}
