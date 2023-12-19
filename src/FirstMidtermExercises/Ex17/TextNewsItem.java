package FirstMidtermExercises.Ex17;

import java.util.Date;

public class TextNewsItem extends NewsItem{
    String description;
    public TextNewsItem(String title, Date datePublished, Category category, String description) {
        super(title, datePublished, category);
        this.description=description;
    }


    public String shortenedDescription() {
        if(description.length()<=80) {
            return description;
        } else {
            return description.substring(0, 80);
        }
    }

    @Override
    String getTeaser() {
        return String.format("%s\n%d\n%s\n", this.getTitle(), uploadedBefore(), shortenedDescription());
    }
}
