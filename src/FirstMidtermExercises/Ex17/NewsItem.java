package FirstMidtermExercises.Ex17;

import java.util.Date;

public abstract class NewsItem {
    private String title;
    Date datePublished;
    Category category;

    public NewsItem(String title, Date datePublished, Category category) {
        this.title = title;
        this.datePublished = datePublished;
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public int uploadedBefore() {
        Date now = new Date();
        return (int) (now.getTime() - datePublished.getTime())/1000/60;
    }
    public String getTitle() {
        return title;
    }

    abstract String getTeaser();

    public String getCategoryString() {
        return category.getCategoryName();
    }
}
