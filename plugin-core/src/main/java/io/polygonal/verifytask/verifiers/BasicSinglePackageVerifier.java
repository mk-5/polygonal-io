package io.polygonal.verifytask.verifiers;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import io.polygonal.Message;
import io.polygonal.plugin.Conditions;
import io.polygonal.plugin.PackageDef;
import io.polygonal.Language;
import io.polygonal.verifytask.dto.ObjectType;
import io.polygonal.verifytask.dto.PackageInformation;
import io.polygonal.verifytask.ports.PackageParser;
import io.polygonal.verifytask.ports.SinglePackageVerifier;

import javax.annotation.Nonnull;
import java.io.File;

class BasicSinglePackageVerifier implements SinglePackageVerifier {

    protected static final int UNLIMITED = -1;
    protected final PackageParser packageParser;

    @Inject
    BasicSinglePackageVerifier(PackageParser packageParser) {
        this.packageParser = packageParser;
    }

    public PackageInformation verify(File packageDir, PackageDef packageDef) {
        if (!packageDir.isDirectory()) {
            if (packageDef.isRequired()) {
                Conditions.check(packageDir.isDirectory(), Message.PACKAGE_REQUIRED.withArgs(packageDef.getName(), packageDir.getPath()));
            } else {
                return new PackageInformation();
            }
        }
        PackageInformation packageInformation = packageParser.parseDirectory(packageDir);
        if (packageDef.getPublicScope() > UNLIMITED) {
            Conditions.check(packageInformation.getPublicObjects() <= packageDef.getPublicScope(), Message.PUBLIC_OBJECTS_ERROR.withArgs(packageDef.getPublicScope(), packageDef.getName()));
        }
        if (packageDef.getPackagePrivateScope() > UNLIMITED) {
            Conditions.check(packageInformation.getPackagePrivateObjects() <= packageDef.getPackagePrivateScope(), Message.PACKAGE_PRIVATE_OBJECTS_ERROR.withArgs(packageDef.getPackagePrivateScope(), packageDef.getName()));
        }
        if (packageDef.getProtectedScope() > UNLIMITED) {
            Conditions.check(packageInformation.getProtectedObjects() <= packageDef.getProtectedScope(), Message.PROTECTED_OBJECTS_ERROR.withArgs(packageDef.getProtectedScope(), packageDef.getName()));
        }
        Conditions.check(packageInformation.getInterfaces() == 0 || (packageDef.getTypes().contains(ObjectType.INTERFACE.lowerCaseName()) && packageInformation.getInterfaces() > 0), Message.INTERFACES_ERROR.withArgs(packageDef.getName()));
        Conditions.check(packageInformation.getClasses() == 0 || (packageDef.getTypes().contains(ObjectType.CLASS.lowerCaseName()) && packageInformation.getClasses() > 0), Message.CLASSES_ERROR.withArgs(packageDef.getName()));
        Conditions.check(packageInformation.getAbstractClasses() == 0 || (packageDef.getTypes().contains(ObjectType.ABSTRACT_CLASS.lowerCaseName()) && packageInformation.getAbstractClasses() > 0), Message.ABSTRACT_CLASSES_ERROR.withArgs(packageDef.getName()));
        Conditions.check(packageInformation.getEnums() == 0 || (packageDef.getTypes().contains(ObjectType.ENUM.lowerCaseName()) && packageInformation.getEnums() > 0), Message.ENUMS_ERROR.withArgs(packageDef.getName()));
        return packageInformation;
    }

    @Override
    public boolean test(@Nonnull Language... languages) {
        return Lists.newArrayList(languages).stream()
                .anyMatch(Language.JAVA::equals);
    }
}
