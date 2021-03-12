package io.polygonal.verifytask.verifiers;

import io.polygonal.Message;
import io.polygonal.plugin.ObjectType;
import io.polygonal.plugin.PackageDef;
import io.polygonal.verifytask.Conditions;
import io.polygonal.verifytask.PackageInformation;
import io.polygonal.verifytask.parsers.PackageParser;

import java.io.File;

class KotlinPackageVerifier extends BasicPackageVerifier {

    KotlinPackageVerifier(PackageParser packageParser) {
        super(packageParser);
    }

    @Override
    public PackageInformation verify(File packageDir, PackageDef packageDefExtension) {
        PackageInformation packageInformation = super.verify(packageDir, packageDefExtension);
        if (packageDefExtension.getInternalScope() > UNLIMITED) {
            Conditions.check(packageInformation.getInternalObjects() <= packageDefExtension.getInternalScope(), Message.INTERNAL_OBJECTS_ERROR.withArgs(packageDefExtension.getInternalScope(), packageDefExtension.getName()));
        }
        Conditions.check(packageInformation.getDataClasses() == 0 || (packageDefExtension.getTypes().contains(ObjectType.DATA_CLASS.lowerCaseName()) && packageInformation.getDataClasses() > 0), Message.DATA_CLASSES_ERROR.withArgs(packageDefExtension.getName()));
        Conditions.check(packageInformation.getOpenClasses() == 0 || (packageDefExtension.getTypes().contains(ObjectType.OPEN_CLASS.lowerCaseName()) && packageInformation.getOpenClasses() > 0), Message.OPEN_CLASSES_ERROR.withArgs(packageDefExtension.getName()));
        return packageInformation;
    }

}
