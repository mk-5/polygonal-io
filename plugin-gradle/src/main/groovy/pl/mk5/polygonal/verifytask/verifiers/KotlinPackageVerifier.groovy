package pl.mk5.polygonal.verifytask.verifiers

import groovy.transform.PackageScope
import pl.mk5.polygonal.Message
import pl.mk5.polygonal.plugin.ObjectType
import pl.mk5.polygonal.plugin.PackageDefExtension
import pl.mk5.polygonal.verifytask.Conditions
import pl.mk5.polygonal.verifytask.models.PackageInformation
import pl.mk5.polygonal.verifytask.parsers.PackageParser

@PackageScope
class KotlinPackageVerifier extends BasicPackageVerifier {

    KotlinPackageVerifier(PackageParser packageParser) {
        super(packageParser)
    }

    @Override
    PackageInformation verify(File packageDir, PackageDefExtension packageDefExtension) {
        def packageInformation = super.verify(packageDir, packageDefExtension)
        if (packageDefExtension.internalScope > UNLIMITED) {
            Conditions.check(packageInformation.internalObjects <= packageDefExtension.internalScope, Message.INTERNAL_OBJECTS_ERROR.withArgs(packageDefExtension.internalScope, packageDefExtension.name))
        }
        Conditions.check(packageInformation.dataClasses == 0 || (packageDefExtension.types.contains(ObjectType.DATA_CLASS.lowerCaseName()) && packageInformation.dataClasses > 0), Message.DATA_CLASSES_ERROR.withArgs(packageDefExtension.name))
        Conditions.check(packageInformation.openClasses == 0 || (packageDefExtension.types.contains(ObjectType.OPEN_CLASS.lowerCaseName()) && packageInformation.openClasses > 0), Message.OPEN_CLASSES_ERROR.withArgs(packageDefExtension.name))
        return packageInformation
    }
}
