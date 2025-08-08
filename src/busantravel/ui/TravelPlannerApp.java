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
    private JTextField searchField; // 검색 필드 추가
    private JButton searchButton; // 검색 버튼 추가
    private JPanel searchAndAuthPanel; // 검색 및 인증 패널

    private String currentTab = "home";
    private HomePanel homePanel;
    private SchedulePanel schedulePanel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TravelPlannerApp().initialize());
    }

    public void initialize() {
        // 패널들 미리 생성
        homePanel = new HomePanel(frame);
        schedulePanel = new SchedulePanel(frame);
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
            ImageIcon originalIcon = new ImageIcon(getClass().getResource("/busantravel/ui/logo.jpg"));
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

        // 검색 UI (상단 우측)
        searchField = new JTextField(15); // 필드 변수 사용
        searchButton = new JButton("검색"); // 필드 변수 사용
        searchButton.setPreferredSize(new Dimension(70, 25));
        searchButton.setForeground(Color.WHITE);
        searchButton.setBackground(new Color(180, 90, 70));
        searchButton.setFocusPainted(false);
        searchButton.setBorderPainted(false);
        searchButton.setFont(new Font("맑은 고딕", Font.BOLD, 11));

        searchAndAuthPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        searchAndAuthPanel.setBackground(Color.WHITE);
        searchAndAuthPanel.add(searchField);
        searchAndAuthPanel.add(searchButton);
        searchAndAuthPanel.add(authButtonsPanel); // 기존 로그인/회원가입 버튼 패널 추가

        // 검색 버튼 액션 리스너
        searchButton.addActionListener(e -> {
            String query = searchField.getText();
            homePanel.performSearch(query.trim());
            currentTab = "home"; // 검색 후 홈 탭으로 이동
            updateContent(); // 홈 탭으로 전환
            updateTopPanelTitle();
            updateNavButtonStyles();
        });

        topAreaPanel.add(searchAndAuthPanel, BorderLayout.EAST);

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
        
        // 탭에 따라 검색창 보이기/숨기기
        boolean isHomeTab = "home".equals(currentTab);
        searchField.setVisible(isHomeTab);
        searchButton.setVisible(isHomeTab);
        
        switch (currentTab) {
            case "home":
                contentPanel.add(homePanel, BorderLayout.CENTER);
                break;
            case "schedule":
                contentPanel.add(schedulePanel, BorderLayout.CENTER);
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
