package sample.Model;

/**
 * Created by jatin1 on 3/23/16.
 */
public class Review {
    private int reviewId;
    private String comment;
    private int rating;
    private String username;
    private String trainNumber;

    public Review(int reviewId, String comment, int rating, String username,
                  String trainNumber) {
        this.reviewId = reviewId;
        this.comment = comment;
        this.rating = rating;
        this.username = username;
        this.trainNumber = trainNumber;
    }
}
