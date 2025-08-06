package busantravel.service;

import busantravel.dao.MemberDAO;

public class UserService { // 로그인 검사
	public static boolean login(String id, String pw) {
		return MemberDAO.loginCheck(id, pw);
	}
	
	public static boolean register(String id, String name, String pw, String email	 ) {
		return MemberDAO.register(id, name, pw, email);
	}
}
