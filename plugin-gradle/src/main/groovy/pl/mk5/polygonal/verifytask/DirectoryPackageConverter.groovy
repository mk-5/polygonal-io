package pl.mk5.polygonal.verifytask

import groovy.transform.PackageScope

@PackageScope
class DirectoryPackageConverter {
    static String convert(File baseDir, File dir) {
        return dir.path.replace(baseDir.path, "")
                .replace(File.separator, ".")
                .replaceAll("^\\.", "")
    }
}
