package moviemad.model;

import java.sql.SQLException;

public class ProdCompany {
	private String companyName;
	private int prodId;

	/**
	 * Constructs a Production Company
	 *
	 * @param prodId, companyName
	 */
	public ProdCompany(int prodId, String companyName) {
		this.companyName = companyName;
		this.prodId = prodId;
	}

	// Returns the Production Company Name
	public String getCompanyName() {
		return this.companyName;
	}

	// Returns the Production Company ID
	public int getProdId() {
		return this.prodId;
	}
}
