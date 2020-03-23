package pl.mk5.polygonal.verifytask.verifiers;

import java.io.File;

import pl.mk5.polygonal.plugin.PackageDef;
import pl.mk5.polygonal.verifytask.models.PackageInformation;

public interface PackageVerifier {
    PackageInformation verify(File packageDir, PackageDef packageDefExtension);
}
