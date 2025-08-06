package busantravel.ui;
import javax.swing.*;
import java.awt.*;

public class TripSchedulePanel extends JPanel {
    private JLabel titleLabel;
    private JLabel dateLabel;
    private JLabel roomCodeLabel;

    public TripSchedulePanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        titleLabel = new JLabel("여행 제목");
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        dateLabel = new JLabel("여행 기간");
        roomCodeLabel = new JLabel("방 코드");

        add(titleLabel);
        add(Box.createVerticalStrut(10));
        add(dateLabel);
        add(Box.createVerticalStrut(10));
        add(roomCodeLabel);
    }

    public void loadTripRoom(TripRoom room) {
        titleLabel.setText("여행 제목: " + room.getTitle());
        dateLabel.setText("여행 기간: " + room.getStartDate() + " ~ " + room.getEndDate());
        roomCodeLabel.setText("방 코드: " + room.getRoomCode());
        // 일정 세우기 UI 추가 구현 가능
    }
}