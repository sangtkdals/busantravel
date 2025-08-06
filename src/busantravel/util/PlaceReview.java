package busantravel.util;

public class PlaceReview {
    private final String authorName;
    private final long rating;
    private final String text;
    private final String relativeTimeDescription;

    public PlaceReview(String authorName, long rating, String text, String relativeTimeDescription) {
        this.authorName = authorName;
        this.rating = rating;
        this.text = text;
        this.relativeTimeDescription = relativeTimeDescription;
    }

    public String getAuthorName() { return authorName; }
    public long getRating() { return rating; }
    public String getText() { return text; }
    public String getRelativeTimeDescription() { return relativeTimeDescription; }

    @Override
    public String toString() {
        return "\n  - Review by " + authorName + " (" + rating + " stars): '" + text + "' (" + relativeTimeDescription + ")";
    }
}