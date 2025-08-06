package busantravel.controller;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import busantravel.auth.MemberSession;
import busantravel.service.MemberService;
import busantravel.ui.LoginDialog;

public class LoginController {
	
	LoginDialog view;
	JDialog dialog;
	
	public LoginController(LoginDialog view, JDialog dialog) {
		this.view = view;
		this.dialog = dialog;
	}
	
	public void handleLogin() {
	String id = view.getId();
	String pw = view.getPw();
	
	if (id.isEmpty() || pw.isEmpty()) {
		JOptionPane.showMessageDialog(dialog, "아이디와 비밀번호를 입력해주세요.", "로그인 오류", JOptionPane.WARNING_MESSAGE);
	} else {
		if (MemberService.login(id, pw)) {
			String name = MemberService.getNameById(id);
			MemberSession.login(id, name);
            JOptionPane.showMessageDialog(dialog, "로그인 성공!");
            dialog.dispose();
		} else {
            JOptionPane.showMessageDialog(dialog, "아이디 또는 비밀번호가 올바르지 않습니다.", "로그인 오류", JOptionPane.ERROR_MESSAGE);
		}
	}
	}
}
