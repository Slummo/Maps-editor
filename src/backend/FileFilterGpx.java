package backend;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class FileFilterGpx extends FileFilter {
    @Override
    public boolean accept(File f) {

        if (f.isDirectory()){
            return true;
        }

        String estensione = this.getExtension(f);

        if(estensione == null){
            return false;
        }

        if(estensione.equals("gpx")){
            return true;
        }

        return false;
    }

    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }

    @Override
    public String getDescription() {
        return ".gpx";
    }
}
