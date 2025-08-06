package busantravel.ui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SchedulePanel extends JPanel {
    private JFrame parentFrame;
    private JPanel upcomingPanel;
    private JPanel pastPanel;
    private TripSchedulePanel tripSchedulePanel;

    public SchedulePanel(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // 좌측 버튼 패널
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton createTripBtn = new JButton("여행 일정 만들기");
        JButton enterCodeBtn = new JButton("참여 코드 입력");

        createTripBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        enterCodeBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        createTripBtn.setMaximumSize(new Dimension(180, 40));
        enterCodeBtn.setMaximumSize(new Dimension(180, 40));

        createTripBtn.setBackground(new Color(222, 146, 124));
        createTripBtn.setForeground(Color.WHITE);
        createTripBtn.setFocusPainted(false);
        enterCodeBtn.setBackground(new Color(222, 146, 124));
        enterCodeBtn.setForeground(Color.WHITE);
        enterCodeBtn.setFocusPainted(false);

        createTripBtn.addActionListener(e -> {
            CreateTripDialog dialog = new CreateTripDialog(parentFrame);
            dialog.setVisible(true);
            refreshRoomLists();
        });

        enterCodeBtn.addActionListener(e -> {
            EnterCodeDialog dialog = new EnterCodeDialog(parentFrame, this);
            dialog.setVisible(true);
        });

        leftPanel.add(createTripBtn);
        leftPanel.add(Box.createVerticalStrut(15));
        leftPanel.add(enterCodeBtn);

        add(leftPanel, BorderLayout.WEST);

        // 오른쪽 여행 목록 및 상세 일정 패널
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 다가오는 여행 패널
        upcomingPanel = new JPanel();
        upcomingPanel.setLayout(new BoxLayout(upcomingPanel, BoxLayout.Y_AXIS));
        upcomingPanel.setBackground(Color.WHITE);
        upcomingPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(222, 146, 124)),
                "다가오는 여행",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("맑은 고딕", Font.BOLD, 18),
                new Color(222, 146, 124)
        ));

        // 지난 여행 패널
        pastPanel = new JPanel();
        pastPanel.setLayout(new BoxLayout(pastPanel, BoxLayout.Y_AXIS));
        pastPanel.setBackground(Color.WHITE);
        pastPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(222, 146, 124)),
                "지난 여행",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("맑은 고딕", Font.BOLD, 18),
                new Color(222, 146, 124)
        ));

        // 스크롤 가능하도록 감싸기
        JScrollPane upcomingScroll = new JScrollPane(upcomingPanel);
        JScrollPane pastScroll = new JScrollPane(pastPanel);

        // 여행 목록을 세로로 배치
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.add(upcomingScroll);
        listPanel.add(Box.createVerticalStrut(20));
        listPanel.add(pastScroll);

        rightPanel.add(listPanel, BorderLayout.CENTER);

        // 상세 일정 패널 (초기 빈 화면)
        tripSchedulePanel = new TripSchedulePanel();
        tripSchedulePanel.setPreferredSize(new Dimension(400, 0));
        rightPanel.add(tripSchedulePanel, BorderLayout.EAST);

        add(rightPanel, BorderLayout.CENTER);

        refreshRoomLists();
    }

    // 방 목록 새로고침 및 분류
    public void refreshRoomLists() {
        upcomingPanel.removeAll();
        pastPanel.removeAll();

        TripRoomManager manager = TripRoomManager.getInstance();
        Map<String, TripRoom> rooms = manager.getAllRooms();

        LocalDate today = LocalDate.now();

        List<TripRoom> upcoming = new ArrayList<>();
        List<TripRoom> past = new ArrayList<>();

        for (TripRoom room : rooms.values()) {
            if (room.getEndDate().isBefore(today)) {
                past.add(room);
            } else {
                upcoming.add(room);
            }
        }

        // 카드 스타일로 추가
        for (TripRoom room : upcoming) {
            upcomingPanel.add(createRoomCard(room));
        }
        for (TripRoom room : past) {
            pastPanel.add(createRoomCard(room));
        }

        upcomingPanel.revalidate();
        upcomingPanel.repaint();
        pastPanel.revalidate();
        pastPanel.repaint();
    }

    // 방 카드 생성
    private JPanel createRoomCard(TripRoom room) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)));
        card.setBackground(Color.WHITE);
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // 색상 원 (다가오는 여행은 파란색, 지난 여행은 주황색)
        Color circleColor = room.getEndDate().isBefore(LocalDate.now()) ? new Color(255, 223, 186) : new Color(173, 216, 230);
        JPanel circle = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(circleColor);
                g.fillOval(0, 0, 40, 40);
            }
        };
        circle.setPreferredSize(new Dimension(40, 40));

        // 텍스트 패널
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel(room.getTitle());
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));

        JLabel dateLabel = new JLabel(room.getStartDate() + " - " + room.getEndDate());
        dateLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        dateLabel.setForeground(Color.GRAY);

        JLabel codeLabel = new JLabel("방 코드 : " + room.getRoomCode());
        codeLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        codeLabel.setForeground(Color.GRAY);

        textPanel.add(titleLabel);
        textPanel.add(dateLabel);
        textPanel.add(codeLabel);

        card.add(circle, BorderLayout.WEST);
        card.add(textPanel, BorderLayout.CENTER);

        card.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tripSchedulePanel.loadTripRoom(room);
            }
        });

        return card;
    }

    // 외부에서 일정 패널에 방 정보 로드 요청 시
    public void loadTripRoom(TripRoom room) {
        tripSchedulePanel.loadTripRoom(room);
    }
}