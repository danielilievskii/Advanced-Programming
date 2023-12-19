package FirstMidtermExercises.Ex17;

public class CategoryNotFoundException extends Exception{
    public CategoryNotFoundException(String categoryName) {
        super(String.format("Category %s was not found", categoryName));

    }
}
