package input;

import java.io.File;
import java.io.FileFilter;
import java.util.LinkedList;
import java.util.List;

public class CSVFileFilter implements CSVSearcer, FileFilter {
    public List<File> listFile = new LinkedList<>();
    @Override
    public List<File> searchCSV(File start) {
        File[] files = start.listFiles(this);
        for(File f : files){
            listFile.add(f);
        }
        return listFile;
    }

    @Override
    public boolean accept(File pathname) {
        return pathname.getName().endsWith(".csv");
    }
}
