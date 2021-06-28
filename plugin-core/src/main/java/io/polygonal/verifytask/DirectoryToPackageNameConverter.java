package io.polygonal.verifytask;

import java.io.File;

class DirectoryToPackageNameConverter {
    private DirectoryToPackageNameConverter() {
    }

    public static String convertToPackageName(File baseDir, File dir) {
        return dir.getPath()
                .replace(baseDir.getPath(), "")
                .replace(File.separator, ".")
                .replaceAll("^\\.", "");
    }
}
