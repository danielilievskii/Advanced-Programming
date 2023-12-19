package FirstMidtermExercises.Ex3;

public interface IFile extends Comparable<IFile> {
    public String getFileName();
    public long getFileSize();
    public String getFileInfo(int indent);
    void sortBySize();
    long findLargestFile();

    @Override
    default int compareTo(IFile o) {
        return Long.compare(getFileSize(), o.getFileSize());
    }

}
