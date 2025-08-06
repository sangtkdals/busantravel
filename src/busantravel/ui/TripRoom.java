package busantravel.ui;

import java.time.LocalDate;

public class TripRoom {
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private String roomCode;

    public TripRoom(String title, LocalDate startDate, LocalDate endDate, String roomCode) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.roomCode = roomCode;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getRoomCode() {
        return roomCode;
    }
}