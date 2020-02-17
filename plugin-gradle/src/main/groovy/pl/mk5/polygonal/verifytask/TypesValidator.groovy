package pl.mk5.polygonal.verifytask

import groovy.transform.PackageScope
import pl.mk5.polygonal.Message
import pl.mk5.polygonal.plugin.PackageDefExtension

@PackageScope
class TypesValidator {

    static final PackageDefExtension defaultPackageDef = new PackageDefExtension()

    static void validate(Set<String> types) {
        types.each { type ->
            Conditions.check(defaultPackageDef.types.contains(type), Message.TYPES_INVALID.withArgs(type, defaultPackageDef.types.toString()))
        }
    }
}
