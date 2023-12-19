package FirstMidtermExercises.Ex3;

import java.util.ArrayList;
import java.util.List;

public class Folder implements IFile{
    List<IFile> lista;
    private String name;
    public Folder(String name) {
        this.name=name;
        this.lista=new ArrayList<>();
    }
    public void addFile(IFile file) throws FileNameExistsException{
        if(lista.stream().anyMatch(f -> f.getFileName().equals(file.getFileName()))) {
            throw new FileNameExistsException(file.getFileName(), name);
        } else {
            lista.add(file);
        }
    }

    @Override
    public String getFileName() {
        return name;
    }

    @Override
    public long getFileSize() {
       return lista.stream().mapToLong(IFile::getFileSize).sum();
    }

    @Override
    public String getFileInfo(int indent) {
        StringBuilder sb =new StringBuilder();

        sb.append(String.format("%sFolder name: %10s Folder size: %10d%n",IndentPrinter.printIndent(indent), getFileName(), getFileSize()));
        for (IFile f : lista) {
            sb.append(f.getFileInfo(indent + 1));
        }
        return sb.toString();
    }
//    @Override
//    public String getFileInfo(int indent) {
//        StringBuilder sb =new StringBuilder();
//        for(int i=0; i<indent; i++) {
//            sb.append("\t");
//        }
//        sb.append(String.format("Folder name: %10s Folder size: %10d%n", getFileName(), getFileSize()));
//        for (IFile f : lista) {
//            sb.append(f.getFileInfo(indent + 1));
//        }
//        return sb.toString();
//    }

    @Override
    public void sortBySize() {
        //lista = lista.stream().sorted(IFile::compareTo).collect(Collectors.toList());
        lista.sort(IFile::compareTo);

        for (IFile f: lista){
            f.sortBySize();
        }
    }

    @Override
    public long findLargestFile() {
        return lista.stream().mapToLong(IFile::findLargestFile).max().orElse(0);
    }

    @Override
    public int compareTo(IFile o) {
        return Long.compare(getFileSize(), o.getFileSize());
    }
}
