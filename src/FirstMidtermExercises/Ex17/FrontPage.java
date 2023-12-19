package FirstMidtermExercises.Ex17;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FrontPage {
    private Category[] categories;
    private List<NewsItem> vesti;

    public FrontPage(Category[] categories) {
        this.categories = categories;
        this.vesti=new ArrayList<>();
    }

    public void addNewsItem(NewsItem newsItem) {
        vesti.add(newsItem);
    }

    public List<NewsItem> listByCategory(Category category) {
        //return vesti.stream().filter(vest -> vest.getCategory().equals(category)).collect(Collectors.toList());
        //return vesti.stream().filter(vest -> vest.getCategory() == category).collect(Collectors.toList());

        return vesti.stream().filter(vest -> vest.getCategoryString().equals(category.getCategoryName())).collect(Collectors.toList());

    }
    public List<NewsItem> listByCategoryName(String category) throws CategoryNotFoundException {

        boolean categoryExists = Arrays.stream(categories)
                .anyMatch(cat -> cat.getCategoryName().equals(category));

        if (!categoryExists) {
            throw new CategoryNotFoundException(category);
        }

        return vesti.stream().filter(vest -> vest.getCategoryString().equals(category)).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        this.vesti.stream().forEach(vest -> {
            sb.append(vest.getTeaser());
        });

        return sb.toString();
    }
}
