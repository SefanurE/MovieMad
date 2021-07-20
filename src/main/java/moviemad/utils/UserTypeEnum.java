package moviemad.utils;

public enum UserTypeEnum {
	pco, critic, basic, admin;

	public static String getFormattedUserType(UserTypeEnum userType) {

		switch (userType) {
		case pco:
			return "PCo";
		case critic:
			return "Critic";
		case basic:
			return "User";
		case admin:
			return "Admin";
		default:
			return "User";
		}
	}

}
