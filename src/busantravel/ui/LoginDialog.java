package busantravel.ui;

import javax.swing.*;
import java.awt.*;

public class LoginDialog {
    private JFrame parent;

    public LoginDialog(JFrame parent) {
        this.parent = parent;
    }

    public void showDialog() {
        JDialog loginDialog = new JDialog(parent, "로그인", true);
        loginDialog.setSize(350, 230); // 다이얼로그 크기 조정
        loginDialog.setLocationRelativeTo(parent);
        loginDialog.setLayout(new GridLayout(3, 2, 15, 15)); // 행/열 간격 조정
        loginDialog.getContentPane().setBackground(Color.WHITE); // 배경색

        JTextField idField = new JTextField();
        JPasswordField pwField = new JPasswordField();

        // 라벨 및 필드 추가
        JLabel idLabel = new JLabel("아이디:");
        idLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        JLabel pwLabel = new JLabel("비밀번호:");
        pwLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));

        loginDialog.add(idLabel);
        loginDialog.add(idField);
        loginDialog.add(pwLabel);
        loginDialog.add(pwField);

        JButton loginBtn = new JButton("로그인");
        JButton cancelBtn = new JButton("취소");

        // 버튼 스타일 설정
        loginBtn.setBackground(new Color(222, 146, 124));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);
        cancelBtn.setBackground(new Color(240, 240, 240));
        cancelBtn.setForeground(new Color(50, 50, 50));
        cancelBtn.setFocusPainted(false);

        loginBtn.addActionListener(e -> {
            // 실제 로그인 로직 (서버 연동 등)은 추후 구현
            String id = idField.getText();
            String pw = new String(pwField.getPassword());
            if (id.isEmpty() || pw.isEmpty()) {
                JOptionPane.showMessageDialog(loginDialog, "아이디와 비밀번호를 입력해주세요.", "로그인 오류", JOptionPane.WARNING_MESSAGE);
            } else if (id.equals("test") && pw.equals("1234")) { // 예시
                JOptionPane.showMessageDialog(loginDialog, "로그인 성공!");
                loginDialog.dispose();
            } else {
                JOptionPane.showMessageDialog(loginDialog, "아이디 또는 비밀번호가 올바르지 않습니다.", "로그인 오류", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelBtn.addActionListener(e -> loginDialog.dispose());

        loginDialog.add(loginBtn);
        loginDialog.add(cancelBtn);

        loginDialog.setVisible(true);
    }
}