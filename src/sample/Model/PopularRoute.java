package sample.Model;

/**
 * Created by Raymond on 4/21/2016.
 */
public class PopularRoute {
    private String train;
    private int count;

    public PopularRoute(String train, int count) {
        this.train = train;
        this.count = count;
    }
    public String getTrain(){
        return train;
    }
    public int getCount() {
        return count;
    }
    @Override public String toString() {
        String a = train + " - " + Integer.toString(count);
        return a;
    }
}
