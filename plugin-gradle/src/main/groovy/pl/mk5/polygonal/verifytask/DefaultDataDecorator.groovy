package pl.mk5.polygonal.verifytask

import groovy.transform.PackageScope
import pl.mk5.polygonal.plugin.PackageDefExtension

@PackageScope
class DefaultDataDecorator {
    final static String ROOT_PACKAGE_NAME = ''

    List<PackageDefExtension> decorate(List<PackageDefExtension> packageDefExtensions) {
        if (!packageDefExtensions.find({ packageDef -> ROOT_PACKAGE_NAME.equals(packageDef.name) })) {
            packageDefExtensions.add(new PackageDefExtension())
        }
        return packageDefExtensions
    }
}
