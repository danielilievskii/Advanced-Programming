package FirstMidtermExercises.Ex8;

import java.time.LocalDate;

public class SpecialArchive extends Archive {
    private int maxOpens;
    private int countOpens;
    public SpecialArchive(int id, int maxOpens) {
        super(id);
        this.maxOpens=maxOpens;
        countOpens=0;
    }

    @Override
    public LocalDate open(LocalDate date) throws InvalidArchiveOpenException {
        if(countOpens>=maxOpens) {
            throw new InvalidArchiveOpenException(String.format("Item %d cannot be opened more than %d times",getID(), maxOpens));
        }
        ++countOpens;
        return date;
    }
}
