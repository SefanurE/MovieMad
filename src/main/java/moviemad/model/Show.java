package moviemad.model;

public class Show {
	private int showID;
	private String showTitle;
	private String genre;
	private double length;
	private String type;
	private int proCoID;
	private String proCoName;
	private int year;
	private String description;
	private String imagePath;
	private String status;
	private String userType;

	/**
	 * Constructs a Show
	 * 
	 * @param showID
	 * @param showTitle
	 * @param genre
	 * @param length
	 * @param movie
	 * @param series
	 * @param proCoID
	 * @param proCoName
	 * @param year
	 * @param description
	 * @param imagePath
	 * @param status
	 * @param userType
	 */
	public Show(int showID, String showTitle, String genre, double length, int movie, int series, int proCoID, 
			int year, String description, String imagePath, String status, String userType, String proCoName) {

		this.showID = showID;
		this.showTitle = showTitle;
		this.genre = genre;
		this.length = length;

		if (movie == 1) {
			this.type = "Movie";
		} else if (series == 1) {
			this.type = "Series";
		}

		this.proCoID = proCoID;
		this.proCoName = proCoName;
		this.year = year;
		this.description = description;
		this.imagePath = imagePath;
		this.status = status;
		this.userType = userType;
	}

	/**
	 * Returns the show ID
	 * 
	 * @return
	 */
	public int getShowID() {
		return showID;
	}

	/**
	 * Returns the show title
	 * 
	 * @return
	 */
	public String getShowTitle() {
		return showTitle;
	}

	/**
	 * Returns the genre of the show
	 * 
	 * @return
	 */
	public String getGenre() {
		return genre;
	}

	/**
	 * returns the length of the show
	 * 
	 * @return
	 */
	public double getLength() {
		return length;
	}

	/**
	 * Returns the show type
	 * 
	 * @return
	 */
	public String getType() {
		return type;
	}

	/**
	 * Returns the production company ID that produced the show
	 *
	 * @return
	 */
	public int getProCoID() {
		return proCoID;
	}
	/**
	 * Returns the year the show first aired
	 * 
	 * @return
	 */
	public int getYear() {
		return year;
	}

	/**
	 * Returns the product company of the show
	 * 
	 * @return
	 */
	public String getProCoName() {
		return proCoName;
	}

	/**
	 * Returns the description of the show
	 *
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Returns the path where the show images are stored
	 *
	 * @return
	 */
	public String getImagePath() {
		return imagePath;
	}

	/**
	 * Returns the status of the show (eg. approved or pending)
	 *
	 * @return
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Returns the user type of the user who submitted the show entry
	 *
	 * @return
	 */
	public String getUserType() {
		return userType;
	}

}