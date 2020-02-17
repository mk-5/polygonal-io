package pl.mk5.polygonal.verifytask.verifiers

import groovy.transform.PackageScope
import pl.mk5.polygonal.Message
import pl.mk5.polygonal.plugin.ObjectType
import pl.mk5.polygonal.plugin.PackageDefExtension
import pl.mk5.polygonal.verifytask.Conditions
import pl.mk5.polygonal.verifytask.models.PackageInformation
import pl.mk5.polygonal.verifytask.parsers.PackageParser

@PackageScope
class BasicPackageVerifier implements PackageVerifier {

    protected static final int UNLIMITED = -1
    protected final PackageParser packageParser

    BasicPackageVerifier(PackageParser packageParser) {
        this.packageParser = packageParser
    }

    PackageInformation verify(File packageDir, PackageDefExtension packageDefExtension) {
        if (!packageDir.isDirectory()) {
            if (packageDefExtension.required) {
                Conditions.check(false, Message.PACKAGE_REQUIRED.withArgs(packageDefExtension.name, packageDir.path))
            } else {
                return
            }
        }
        PackageInformation packageInformation = packageParser.parse(packageDir)
        if (packageDefExtension.publicScope > UNLIMITED) {
            Conditions.check(packageInformation.publicObjects <= packageDefExtension.publicScope, Message.PUBLIC_OBJECTS_ERROR.withArgs(packageDefExtension.publicScope, packageDefExtension.name))
        }
        if (packageDefExtension.packagePrivateScope > UNLIMITED) {
            Conditions.check(packageInformation.packagePrivateObjects <= packageDefExtension.packagePrivateScope, Message.PACKAGE_PRIVATE_OBJECTS_ERROR.withArgs(packageDefExtension.packagePrivateScope, packageDefExtension.name))
        }
        if (packageDefExtension.protectedScope > UNLIMITED) {
            Conditions.check(packageInformation.protectedObjects <= packageDefExtension.protectedScope, Message.PROTECTED_OBJECTS_ERROR.withArgs(packageDefExtension.protectedScope, packageDefExtension.name))
        }
        Conditions.check(packageInformation.interfaces == 0 || (packageDefExtension.types.contains(ObjectType.INTERFACE.lowerCaseName()) && packageInformation.interfaces > 0), Message.INTERFACES_ERROR.withArgs(packageDefExtension.name))
        Conditions.check(packageInformation.classes == 0 || (packageDefExtension.types.contains(ObjectType.CLASS.lowerCaseName()) && packageInformation.classes > 0), Message.CLASSES_ERROR.withArgs(packageDefExtension.name))
        Conditions.check(packageInformation.abstractClasses == 0 || (packageDefExtension.types.contains(ObjectType.ABSTRACT_CLASS.lowerCaseName()) && packageInformation.abstractClasses > 0), Message.ABSTRACT_CLASSES_ERROR.withArgs(packageDefExtension.name))
        Conditions.check(packageInformation.enums == 0 || (packageDefExtension.types.contains(ObjectType.ENUM.lowerCaseName()) && packageInformation.enums > 0), Message.ENUMS_ERROR.withArgs(packageDefExtension.name))
        return packageInformation
    }
}
