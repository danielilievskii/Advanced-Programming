package FirstMidtermExercises.Ex8;

import java.time.LocalDate;
import java.util.Date;

public class LockedArchive extends Archive {
    private LocalDate lockedUntilDate;
    public LockedArchive(int id, LocalDate date) {
        super(id);
        this.lockedUntilDate=date;
    }


    @Override
    public LocalDate open(LocalDate date) throws InvalidArchiveOpenException {
        if(date.isBefore(lockedUntilDate)) {
            throw new InvalidArchiveOpenException(String.format("Item %d cannot be opened before %s", getID(), lockedUntilDate));
        }
        return date;
    }
}
