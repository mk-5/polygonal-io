package pl.mk5.polygonal.verifytask

import groovy.io.FileType
import groovy.transform.PackageScope
import pl.mk5.polygonal.polygons.PackageDefExtension

@PackageScope
class PackagesSplitter {
    Map<File, PackageDefExtension> walkAndSplit(File rootLevel, List<PackageDefExtension> packageDefExtensions) {
        Map<File, PackageDefExtension> defExtensionMap = new HashMap<>()
        if (packageDefExtensions.isEmpty()) {
            return defExtensionMap
        }
        def rootLevelDefIndex = packageDefExtensions.findIndexOf({ packageDef -> packageDef.name.equals('') })
        if (rootLevelDefIndex > -1) {
            defExtensionMap.put(rootLevel, packageDefExtensions.remove(rootLevelDefIndex))
        }
        rootLevel.eachFileRecurse(FileType.DIRECTORIES, { dir ->
            def baseName = DirectoryPackageConverter.convert(rootLevel, dir)
            def packageDefIndex = packageDefExtensions.findIndexOf({ packageDef -> packageDef.name.equals(baseName) })
            if (packageDefIndex > -1) {
                defExtensionMap.put(dir, packageDefExtensions.remove(packageDefIndex))
            }
        })
        return defExtensionMap;
    }
}
