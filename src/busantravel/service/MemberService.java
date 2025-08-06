package busantravel.service;

import busantravel.dao.MemberDAO;

	//로그인 검사
public class MemberService { 
	public static boolean login(String id, String pw) {
		return MemberDAO.loginCheck(id, pw);
	}
	
	// ID 중복 체크 및 회원가입
	public static boolean register(String id, String name, String pw, String email	 ) {
		if (MemberDAO.checkID(id)) {
			return false;
		}
		return MemberDAO.register(id, name, pw, email);
	}
	
	//ID로 이름 찾기
	public static String getNameById(String id) {
		return MemberDAO.getNameById(id);
	}
}
