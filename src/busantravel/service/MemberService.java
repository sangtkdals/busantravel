package busantravel.service;

import busantravel.dao.MemberDAO;
import busantravel.model.Member;

	//로그인 검사
public class MemberService { 
	public static Member login(String id, String pw) {
		return MemberDAO.getMember(id, pw);
	}
	
	// ID 중복 체크 및 회원가입
	public static boolean register(Member member) {
		if (MemberDAO.checkID(member.getM_id())) {
			return false;
		}
		return MemberDAO.register(member);
	}
}
