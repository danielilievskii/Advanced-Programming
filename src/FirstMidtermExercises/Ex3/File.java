package FirstMidtermExercises.Ex3;

public class File implements IFile{
    private String name;
    private long size;
    public File(String name, long size) {
        this.name=name;
        this.size=size;
    }

    @Override
    public String getFileName() {
        return name;
    }

    @Override
    public long getFileSize() {
        return size;
    }

    @Override
    public String getFileInfo(int indent) {
        StringBuilder sb =new StringBuilder();

        sb.append(String.format("%sFile name %10s File Size: %10d%n",IndentPrinter.printIndent(indent), getFileName(), getFileSize()));
        return sb.toString();
    }

    @Override
    public void sortBySize() {
        //nothing
    }


    @Override
    public long findLargestFile() {
        return size;
    }
}
