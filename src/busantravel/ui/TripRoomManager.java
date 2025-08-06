package busantravel.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TripRoomManager {
    private static TripRoomManager instance;
    private Map<String, TripRoom> rooms;
    private Random random;

    private TripRoomManager() {
        rooms = new HashMap<>();
        random = new Random();
    }

    public static synchronized TripRoomManager getInstance() {
        if (instance == null) {
            instance = new TripRoomManager();
        }
        return instance;
    }

    public String generateUniqueRoomCode() {
        String code;
        do {
            code = randomCode(6);
        } while (rooms.containsKey(code));
        return code;
    }

    private String randomCode(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    public void addRoom(TripRoom room) {
        rooms.put(room.getRoomCode(), room);
    }

    public TripRoom getRoom(String roomCode) {
        return rooms.get(roomCode);
    }

    public Map<String, TripRoom> getAllRooms() {
        return rooms;
    }
}