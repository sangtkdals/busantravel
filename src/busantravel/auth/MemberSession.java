package busantravel.auth;

public class MemberSession {
	private static String loginId;
	private static String loginName;
	
	public static void login(String id, String name) {
		loginId = id;
		loginName = name;
	}
	
	public static void logout() {
		loginId = null;
		loginName = null;
	}
	
	public static String getId() {
		return loginId;
	}
	
	public static String getName() {
		return loginName;
	}
	
	public static boolean isLoggedIn() {
		if (loginId != null) return true;
		else return false;
	}
}
