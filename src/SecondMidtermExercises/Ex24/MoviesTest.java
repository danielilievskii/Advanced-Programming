package SecondMidtermExercises.Ex24;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

class Movie {
    String title;
    List<Integer> ratings;

    public Movie(String title, int[] ratings) {
        this.title = title;
        this.ratings=new ArrayList<>();
        for(int r : ratings) {
            this.ratings.add(r);
        }
    }

    public String getTitle() {
        return title;
    }

    public List<Integer> getRatings() {
        return ratings;
    }

    public double getAverageRating() {
        return ratings.stream().mapToDouble(i -> i).average().orElse(0.0);
    }

    public double getCoefRating(int maxNumRatings) {
        return getAverageRating() * (getNumRatings()*1.0) / (maxNumRatings*1.0);
    }

    public int getNumRatings() {
        return ratings.size();
    }

    @Override
    public String toString() {
        return String.format("%s (%.2f) of %d ratings", title, getAverageRating(), getNumRatings());
    }
}
class MoviesList {

    List<Movie> movies;

    public MoviesList() {
        this.movies=new ArrayList<>();
    }
    public void addMovie(String title, int[] ratings) {
        Movie m = new Movie(title, ratings);
        movies.add(m);
    }

    public List<Movie> top10ByAvgRating() {
        return movies.stream()
                .sorted(Comparator.comparing(Movie::getAverageRating).reversed().thenComparing(Movie::getTitle))
                .limit(10)
                .collect(toList());
    }

    public List<Movie> top10ByRatingCoef() {
        int maxNumRatings = movies.stream().mapToInt(Movie::getNumRatings).max().getAsInt();
        Comparator<Movie> movieComparator = Comparator.comparing(movie -> movie.getCoefRating(maxNumRatings));
        return movies.stream()
                .sorted(movieComparator.reversed().thenComparing(Movie::getTitle))
                .limit(10)
                .collect(toList());
    }
}

public class MoviesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MoviesList moviesList = new MoviesList();
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String title = scanner.nextLine();
            int x = scanner.nextInt();
            int[] ratings = new int[x];
            for (int j = 0; j < x; ++j) {
                ratings[j] = scanner.nextInt();
            }
            scanner.nextLine();
            moviesList.addMovie(title, ratings);
        }
        scanner.close();
        List<Movie> movies = moviesList.top10ByAvgRating();
        System.out.println("=== TOP 10 BY AVERAGE RATING ===");
        for (Movie movie : movies) {
            System.out.println(movie);
        }
        movies = moviesList.top10ByRatingCoef();
        System.out.println("=== TOP 10 BY RATING COEFFICIENT ===");
        for (Movie movie : movies) {
            System.out.println(movie);
        }
    }
}

