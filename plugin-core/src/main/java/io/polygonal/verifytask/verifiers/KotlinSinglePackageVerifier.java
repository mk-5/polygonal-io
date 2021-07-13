package io.polygonal.verifytask.verifiers;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import io.polygonal.Message;
import io.polygonal.verifytask.dto.ObjectType;
import io.polygonal.plugin.PackageDef;
import io.polygonal.plugin.Conditions;
import io.polygonal.verifytask.dto.PackageInformation;
import io.polygonal.Language;
import io.polygonal.verifytask.ports.PackageParser;

import javax.annotation.Nonnull;
import java.io.File;

class KotlinSinglePackageVerifier extends BasicSinglePackageVerifier {

    @Inject
    KotlinSinglePackageVerifier(PackageParser packageParser) {
        super(packageParser);
    }

    @Override
    public PackageInformation verify(File packageDir, PackageDef packageDef) {
        PackageInformation packageInformation = super.verify(packageDir, packageDef);
        if (packageDef.getInternalScope() > UNLIMITED) {
            Conditions.check(packageInformation.getInternalObjects() <= packageDef.getInternalScope(), Message.INTERNAL_OBJECTS_ERROR.withArgs(packageDef.getInternalScope(), packageDef.getName()));
        }
        Conditions.check(packageInformation.getDataClasses() == 0 || (packageDef.getTypes().contains(ObjectType.DATA_CLASS.lowerCaseName()) && packageInformation.getDataClasses() > 0), Message.DATA_CLASSES_ERROR.withArgs(packageDef.getName()));
        Conditions.check(packageInformation.getOpenClasses() == 0 || (packageDef.getTypes().contains(ObjectType.OPEN_CLASS.lowerCaseName()) && packageInformation.getOpenClasses() > 0), Message.OPEN_CLASSES_ERROR.withArgs(packageDef.getName()));
        return packageInformation;
    }

    @Override
    public boolean test(@Nonnull Language... languages) {
        return Lists.newArrayList(languages).stream()
                .anyMatch(Language.KOTLIN::equals);
    }
}
