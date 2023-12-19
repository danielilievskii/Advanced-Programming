package FirstMidtermExercises.Ex17;

import java.util.Date;

public class MediaNewsItem extends NewsItem{
    private String url;
    private int viewCount;


    public MediaNewsItem(String title, Date datePublished, Category category, String url, int viewCount) {
        super(title, datePublished, category);
        this.url=url;
        this.viewCount=viewCount;
    }

    @Override
    String getTeaser() {
        return String.format("%s\n%d\n%s\n%d\n", this.getTitle(), uploadedBefore(), this.url, this.viewCount);
    }
}
