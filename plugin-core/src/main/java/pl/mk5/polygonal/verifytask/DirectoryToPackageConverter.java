package pl.mk5.polygonal.verifytask;

import java.io.File;

class DirectoryToPackageConverter {
    public static String convert(File baseDir, File dir) {
        return dir.getPath()
                .replace(baseDir.getPath(), "")
                .replace(File.separator, ".")
                .replaceAll("^\\.", "");
    }
}
