package FirstMidtermExercises.Ex8;

import java.time.LocalDate;
import java.time.LocalDateTime;


public abstract class Archive {
    private int ID;
    private LocalDate dateArchived;

    public abstract LocalDate open(LocalDate date) throws InvalidArchiveOpenException;

    public Archive(int ID) {
        this.ID = ID;
    }

    public void archive(LocalDate date) {
        this.dateArchived=date;
    }

    public int getID() {
        return ID;
    }
    public LocalDate getDateArchived() {
        return dateArchived;
    }
}
