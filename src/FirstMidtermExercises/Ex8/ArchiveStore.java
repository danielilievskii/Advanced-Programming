package FirstMidtermExercises.Ex8;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class

ArchiveStore {
    private List<Archive> archives;
    public StringBuilder log;
    public ArchiveStore() {
        this.archives=new ArrayList<>();
        this.log = new StringBuilder();
    }

    public void archiveItem(Archive item, LocalDate date) {
        item.archive(date);
        archives.add(item);
        log.append(String.format("Item %d archived at %s\n", item.getID(), date.toString()));
    }

    public void openItem(int id, LocalDate date) throws NonExistingItemException {
        boolean found=false;
        for(Archive a: archives) {
            if(a.getID()==id) {
                try {
                    a.open(date);
                } catch (InvalidArchiveOpenException e) {
                    log.append(e.getMessage());
                    log.append("\n");
                    return;
                }
                found=true;
                log.append(String.format("Item %d opened at %s\n", a.getID(), date));
            }
        }

        if(!found) {
            throw new NonExistingItemException(id);
        }
    }
    public String getLog() {
        return log.toString();
    }
}
