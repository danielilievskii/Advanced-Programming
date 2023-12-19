package FirstMidtermExercises.Ex3;

public class FileSystem {
    private Folder rootDirectory;

    public FileSystem() {
        this.rootDirectory = new Folder("Root");
    }

    public void addFile(IFile file) throws FileNameExistsException {
        rootDirectory.addFile(file);
//        try{
//
//        } catch (FileNameExistsException e) {
//            System.out.println(e.getMessage());
//        }
    }
    public long findLargestFile() {
        return rootDirectory.findLargestFile();
    }

    public void sortBySize() {
        rootDirectory.sortBySize();
    }

    @Override
    public String toString() {
        return rootDirectory.getFileInfo(0);
    }
}
