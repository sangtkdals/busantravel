package busantravel.ui;

public class PlaceDetailInfo {
    private String name;
    private String description;
    private String imagePath; // 이미지 파일 경로 (예: "haeundae.jpg")

    public PlaceDetailInfo(String name, String description, String imagePath) {
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImagePath() {
        return imagePath;
    }
}