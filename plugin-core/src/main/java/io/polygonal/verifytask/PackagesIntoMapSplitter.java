package io.polygonal.verifytask;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import io.polygonal.plugin.PackageDef;
import lombok.SneakyThrows;

class PackagesIntoMapSplitter {
    private PackagesIntoMapSplitter() {
    }

    @SneakyThrows
    static Map<File, PackageDef> splitPackagesIntoMap(File rootLevel, List<PackageDef> packageDefExtensions) {
        Map<File, PackageDef> defExtensionMap = new HashMap<>();
        if (packageDefExtensions.isEmpty()) {
            return defExtensionMap;
        }
        List<PackageDef> sourcePackageDefExtensions = new ArrayList<>(packageDefExtensions);
        Optional<PackageDef> rootLevelDef = sourcePackageDefExtensions.stream()
                .filter(packageDef -> "".equals(packageDef.getName()))
                .findAny();
        rootLevelDef.ifPresent(packageDef -> defExtensionMap.put(rootLevel, packageDef));
        Files.walk(rootLevel.toPath())
                .filter(Files::isDirectory)
                .map(Path::toFile)
                .forEach(dir -> {
                    Optional<PackageDef> packageDef = findPackageDef(rootLevel, dir, sourcePackageDefExtensions);
                    packageDef.ifPresent(def -> defExtensionMap.put(dir, def));
                });
        return defExtensionMap;
    }

    private static Optional<PackageDef> findPackageDef(File rootDir, File dir, List<PackageDef> packageDefs) {
        String dirPackageName = DirectoryToPackageNameConverter.convertToPackageName(rootDir, dir);
        return packageDefs.stream()
                .filter(p -> dirPackageName.equals(p.getName()))
                .findAny();
    }
}
