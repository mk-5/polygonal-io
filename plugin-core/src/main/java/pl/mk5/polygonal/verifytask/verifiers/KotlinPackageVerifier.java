package pl.mk5.polygonal.verifytask.verifiers;

import java.io.File;

import pl.mk5.polygonal.Message;
import pl.mk5.polygonal.plugin.ObjectType;
import pl.mk5.polygonal.plugin.PackageDef;
import pl.mk5.polygonal.verifytask.Conditions;
import pl.mk5.polygonal.verifytask.models.PackageInformation;
import pl.mk5.polygonal.verifytask.parsers.PackageParser;

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
