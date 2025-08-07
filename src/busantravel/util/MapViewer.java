package busantravel.util;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 * 이미지 URL을 받아 GUI 창(JFrame)에 해당 이미지를 표시하는 클래스입니다.
 */
public class MapViewer {

    /**
     * 지정된 이미지 URL을 사용하여 지도 이미지를 새로운 창에 표시합니다.
     * Swing GUI 작업은 Event Dispatch Thread(EDT)에서 실행되도록 보장합니다.
     *
     * @param imageUrl 표시할 이미지의 완전한 URL
     */
    public static void showMap(String imageUrl) {
        // Swing GUI는 항상 Event Dispatch Thread에서 생성하고 수정해야 합니다.
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Static Map Viewer");
            
            // 창을 닫을 때 전체 프로그램이 종료되지 않고 해당 창만 닫히도록 설정합니다.
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 

            try {
                URL url = new URL(imageUrl);
                // URL로부터 이미지를 읽어옵니다. 이 과정에서 네트워크 통신이 발생합니다.
                Image image = ImageIO.read(url);

                if (image != null) {
                    ImageIcon imageIcon = new ImageIcon(image);
                    JLabel imageLabel = new JLabel(imageIcon);
                    frame.add(imageLabel);
                } else {
                    frame.add(new JLabel("이미지를 로드할 수 없습니다. URL을 확인하세요."));
                }
            } catch (IOException e) {
                e.printStackTrace();
                frame.add(new JLabel("오류: 이미지를 로드하는 중 문제가 발생했습니다."));
            }

            frame.pack(); // 프레임 안의 내용물(이미지)에 맞게 창 크기를 자동으로 조절합니다.
            frame.setLocationRelativeTo(null); // 창을 화면 중앙에 위치시킵니다.
            frame.setVisible(true); // 창을 화면에 보이게 합니다.
        });
    }
}