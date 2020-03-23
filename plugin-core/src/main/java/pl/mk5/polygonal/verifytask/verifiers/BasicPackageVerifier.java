package pl.mk5.polygonal.verifytask.verifiers;

import java.io.File;

import pl.mk5.polygonal.Message;
import pl.mk5.polygonal.plugin.ObjectType;
import pl.mk5.polygonal.plugin.PackageDef;
import pl.mk5.polygonal.verifytask.Conditions;
import pl.mk5.polygonal.verifytask.models.PackageInformation;
import pl.mk5.polygonal.verifytask.parsers.PackageParser;

class BasicPackageVerifier implements PackageVerifier {

    protected static final int UNLIMITED = -1;
    protected final PackageParser packageParser;

    BasicPackageVerifier(PackageParser packageParser) {
        this.packageParser = packageParser;
    }

    public PackageInformation verify(File packageDir, PackageDef packageDefExtension) {
        if (!packageDir.isDirectory()) {
            if (packageDefExtension.isRequired()) {
                Conditions.check(false, Message.PACKAGE_REQUIRED.withArgs(packageDefExtension.getName(), packageDir.getPath()));
            } else {
                return new PackageInformation();
            }
        }
        PackageInformation packageInformation = packageParser.parse(packageDir);
        if (packageDefExtension.getPublicScope() > UNLIMITED) {
            Conditions.check(packageInformation.getPublicObjects() <= packageDefExtension.getPublicScope(), Message.PUBLIC_OBJECTS_ERROR.withArgs(packageDefExtension.getPublicScope(), packageDefExtension.getName()));
        }
        if (packageDefExtension.getPackagePrivateScope() > UNLIMITED) {
            Conditions.check(packageInformation.getPackagePrivateObjects() <= packageDefExtension.getPackagePrivateScope(), Message.PACKAGE_PRIVATE_OBJECTS_ERROR.withArgs(packageDefExtension.getPackagePrivateScope(), packageDefExtension.getName()));
        }
        if (packageDefExtension.getProtectedScope() > UNLIMITED) {
            Conditions.check(packageInformation.getProtectedObjects() <= packageDefExtension.getProtectedScope(), Message.PROTECTED_OBJECTS_ERROR.withArgs(packageDefExtension.getProtectedScope(), packageDefExtension.getName()));
        }
        Conditions.check(packageInformation.getInterfaces() == 0 || (packageDefExtension.getTypes().contains(ObjectType.INTERFACE.lowerCaseName()) && packageInformation.getInterfaces() > 0), Message.INTERFACES_ERROR.withArgs(packageDefExtension.getName()));
        Conditions.check(packageInformation.getClasses() == 0 || (packageDefExtension.getTypes().contains(ObjectType.CLASS.lowerCaseName()) && packageInformation.getClasses() > 0), Message.CLASSES_ERROR.withArgs(packageDefExtension.getName()));
        Conditions.check(packageInformation.getAbstractClasses() == 0 || (packageDefExtension.getTypes().contains(ObjectType.ABSTRACT_CLASS.lowerCaseName()) && packageInformation.getAbstractClasses() > 0), Message.ABSTRACT_CLASSES_ERROR.withArgs(packageDefExtension.getName()));
        Conditions.check(packageInformation.getEnums() == 0 || (packageDefExtension.getTypes().contains(ObjectType.ENUM.lowerCaseName()) && packageInformation.getEnums() > 0), Message.ENUMS_ERROR.withArgs(packageDefExtension.getName()));
        return packageInformation;
    }
}
