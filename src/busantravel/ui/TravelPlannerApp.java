package busantravel.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TravelPlannerApp {
    private JFrame frame;
    private JPanel mainPanel;
    private JPanel contentPanel;
    private JPanel navigationPanel;
    private JLabel currentTitleLabel;
    private JLabel logoLabel;

    private String currentTab = "home";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TravelPlannerApp().initialize());
    }

    public void initialize() {
        frame = new JFrame("부산 여행 플래너");
        frame.setSize(1000, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        mainPanel = new JPanel(new BorderLayout());

        // 상단 패널
        JPanel topAreaPanel = new JPanel(new BorderLayout());
        topAreaPanel.setBackground(Color.WHITE);

        // 로고 이미지 (BusanHow1 패키지 내 logo.jpg)
        try {
            ImageIcon originalIcon = new ImageIcon(getClass().getResource("/BusanHow1/logo.jpg"));
            Image originalImage = originalIcon.getImage();
            int targetWidth = 150;
            double aspectRatio = (double) originalImage.getHeight(null) / originalImage.getWidth(null);
            int targetHeight = (int) (targetWidth * aspectRatio);
            Image scaledImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
            logoLabel = new JLabel(new ImageIcon(scaledImage));
        } catch (Exception e) {
            System.err.println("로고 이미지 파일을 찾을 수 없습니다: " + e.getMessage());
            logoLabel = new JLabel("<html><font color='#222222'>부산</font><font color='#DE927C'>어때</font></html>");
            logoLabel.setFont(new Font("맑은 고딕", Font.BOLD, 26));
        }
        logoLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        logoLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // 로고 클릭 시 여행 홈으로 이동
        logoLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                currentTab = "home";
                updateContent();
                updateTopPanelTitle();
                updateNavButtonStyles();
            }
        });

        topAreaPanel.add(logoLabel, BorderLayout.WEST);

        // 동적 타이틀
        currentTitleLabel = new JLabel("", SwingConstants.CENTER);
        currentTitleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 24));
        currentTitleLabel.setForeground(new Color(50, 50, 50));
        topAreaPanel.add(currentTitleLabel, BorderLayout.CENTER);

        // 로그인/회원가입 버튼
        JPanel authButtonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        authButtonsPanel.setBackground(Color.WHITE);
        JButton loginButton = new JButton("로그인");
        JButton signUpButton = new JButton("회원가입");

        loginButton.setBackground(new Color(240, 240, 240));
        loginButton.setForeground(new Color(50, 50, 50));
        signUpButton.setBackground(new Color(240, 240, 240));
        signUpButton.setForeground(new Color(50, 50, 50));
        loginButton.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        signUpButton.setFont(new Font("맑은 고딕", Font.PLAIN, 12));

        loginButton.addActionListener(e -> new LoginDialog(frame).showDialog());
        signUpButton.addActionListener(e -> new SignUpDialog(frame).showDialog());

        authButtonsPanel.add(loginButton);
        authButtonsPanel.add(signUpButton);
        topAreaPanel.add(authButtonsPanel, BorderLayout.EAST);

        mainPanel.add(topAreaPanel, BorderLayout.NORTH);

        // contentPanel은 일반 JPanel로 유지 (스크롤 제거)
        contentPanel = new JPanel(new BorderLayout());
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // 네비게이션 바
        navigationPanel = new JPanel(new GridLayout(1, 4));
        navigationPanel.setBackground(new Color(222, 146, 124));
        initNavigationButtons();

        mainPanel.add(navigationPanel, BorderLayout.SOUTH);

        // 초기 화면 세팅
        updateContent();
        updateTopPanelTitle();
        updateNavButtonStyles();

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private void initNavigationButtons() {
        navigationPanel.add(createNavButton("여행 홈", "home"));
        navigationPanel.add(createNavButton("일정", "schedule"));
        navigationPanel.add(createNavButton("저장", "saved"));
        navigationPanel.add(createNavButton("후기", "review"));
    }

    private JButton createNavButton(String text, String tabName) {
        JButton button = new JButton(text);
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(222, 146, 124));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("맑은 고딕", Font.BOLD, 14));

        button.addActionListener(e -> {
            currentTab = tabName;
            updateContent();
            updateTopPanelTitle();
            updateNavButtonStyles();
        });
        return button;
    }

    private void updateNavButtonStyles() {
        for (Component comp : navigationPanel.getComponents()) {
            if (comp instanceof JButton) {
                JButton btn = (JButton) comp;
                btn.setBackground(btn.getText().equals(getTabText(currentTab)) ? new Color(180, 90, 70) : new Color(222, 146, 124));
            }
        }
    }

    private String getTabText(String tabName) {
        switch (tabName) {
            case "home": return "여행 홈";
            case "schedule": return "일정";
            case "saved": return "저장";
            case "review": return "후기";
            default: return "";
        }
    }

    private void updateContent() {
        contentPanel.removeAll();
        switch (currentTab) {
            case "home":
                contentPanel.add(new HomePanel(frame), BorderLayout.CENTER);
                break;
            case "schedule":
                contentPanel.add(new SchedulePanel(frame), BorderLayout.CENTER);
                break;
            case "saved":
                contentPanel.add(createPlaceholderPanel("저장된 장소 화면 (추후 구현)"), BorderLayout.CENTER);
                break;
            case "review":
                contentPanel.add(createPlaceholderPanel("후기 화면 (추후 구현)"), BorderLayout.CENTER);
                break;
        }
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void updateTopPanelTitle() {
        currentTitleLabel.setText(getTabText(currentTab));
    }

    private JPanel createPlaceholderPanel(String text) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.add(new JLabel(text, SwingConstants.CENTER));
        panel.setBackground(Color.WHITE);
        return panel;
    }
}