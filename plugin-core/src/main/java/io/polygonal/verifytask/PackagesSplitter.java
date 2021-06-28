package io.polygonal.verifytask;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import io.polygonal.plugin.PackageDef;
import lombok.SneakyThrows;

class PackagesSplitter {
    private PackagesSplitter() {
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
                .forEach(dir -> {
                    String baseName = DirectoryToPackageNameConverter.convertToPackageName(rootLevel, dir.toFile());
                    Optional<PackageDef> packageDef = sourcePackageDefExtensions.stream()
                            .filter(p -> baseName.equals(p.getName()))
                            .findAny();
                    packageDef.ifPresent(def -> defExtensionMap.put(dir.toFile(), def));
                });
        return defExtensionMap;
    }
}
