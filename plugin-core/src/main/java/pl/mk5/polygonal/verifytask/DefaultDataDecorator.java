package pl.mk5.polygonal.verifytask;

import java.util.List;
import java.util.Optional;

import pl.mk5.polygonal.plugin.PackageDef;

class DefaultDataDecorator {
    private final static String ROOT_PACKAGE_NAME = "";

    List<PackageDef> decorate(List<PackageDef> packageDefExtensions) {
        Optional<PackageDef> rootPackage = packageDefExtensions.stream()
                .filter(p -> ROOT_PACKAGE_NAME.equals(p.getName()))
                .findAny();
        if (!rootPackage.isPresent()) {
            PackageDef rootDef = new PackageDef();
            rootDef.setPackagePrivateScope(-1);
            packageDefExtensions.add(rootDef);
        }
        return packageDefExtensions;
    }
}
