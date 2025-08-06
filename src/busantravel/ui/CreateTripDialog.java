package busantravel.ui;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class CreateTripDialog extends JDialog {
    private JTextField titleField;
    private JDateChooser startDateChooser;
    private JDateChooser endDateChooser;

    public CreateTripDialog(JFrame parent) {
        super(parent, "여행 일정 만들기", true);
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(5, 2, 10, 10));
        setResizable(false);

        add(new JLabel("여행 제목:"));
        titleField = new JTextField();
        add(titleField);

        add(new JLabel("시작 날짜:"));
        startDateChooser = new JDateChooser();
        add(startDateChooser);

        add(new JLabel("종료 날짜:"));
        endDateChooser = new JDateChooser();
        add(endDateChooser);

        JButton createBtn = new JButton("만들기");
        JButton cancelBtn = new JButton("취소");

        createBtn.addActionListener(e -> {
            String title = titleField.getText().trim();
            Date startDate = startDateChooser.getDate();
            Date endDate = endDateChooser.getDate();

            if (title.isEmpty() || startDate == null || endDate == null) {
                JOptionPane.showMessageDialog(this, "모든 항목을 입력해주세요.", "입력 오류", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (endDate.before(startDate)) {
                JOptionPane.showMessageDialog(this, "종료 날짜는 시작 날짜 이후여야 합니다.", "날짜 오류", JOptionPane.WARNING_MESSAGE);
                return;
            }

            LocalDate startLocalDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate endLocalDate = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            TripRoomManager manager = TripRoomManager.getInstance();
            String roomCode = manager.generateUniqueRoomCode();
            TripRoom newRoom = new TripRoom(title, startLocalDate, endLocalDate, roomCode);
            manager.addRoom(newRoom);

            JOptionPane.showMessageDialog(this, "방이 생성되었습니다! 방 코드: " + roomCode);
            dispose();
        });

        cancelBtn.addActionListener(e -> dispose());

        add(createBtn);
        add(cancelBtn);
    }
}