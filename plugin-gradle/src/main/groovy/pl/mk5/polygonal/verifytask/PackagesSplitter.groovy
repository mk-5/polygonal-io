package pl.mk5.polygonal.verifytask

import groovy.io.FileType
import groovy.transform.PackageScope
import pl.mk5.polygonal.plugin.PackageDefExtension

@PackageScope
class PackagesSplitter {
    Map<File, PackageDefExtension> walkAndSplit(File rootLevel, List<PackageDefExtension> packageDefExtensions) {
        Map<File, PackageDefExtension> defExtensionMap = new HashMap<>()
        def sourcePackageDefExtensions = new ArrayList<>(packageDefExtensions)
        if (packageDefExtensions.isEmpty()) {
            return defExtensionMap
        }
        def rootLevelDefIndex = sourcePackageDefExtensions.findIndexOf({ packageDef -> packageDef.name.equals('') })
        if (rootLevelDefIndex > -1) {
            defExtensionMap.put(rootLevel, sourcePackageDefExtensions.remove(rootLevelDefIndex))
        }
        rootLevel.eachFileRecurse(FileType.DIRECTORIES, { dir ->
            def baseName = DirectoryPackageConverter.convert(rootLevel, dir)
            def packageDefIndex = sourcePackageDefExtensions.findIndexOf({ packageDef -> packageDef.name.equals(baseName) })
            if (packageDefIndex > -1) {
                defExtensionMap.put(dir, sourcePackageDefExtensions.remove(packageDefIndex))
            }
        })
        return defExtensionMap;
    }
}
