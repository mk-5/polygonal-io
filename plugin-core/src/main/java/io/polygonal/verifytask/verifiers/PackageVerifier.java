package io.polygonal.verifytask.verifiers;

import io.polygonal.plugin.PackageDef;
import io.polygonal.verifytask.PackageInformation;

import java.io.File;

public interface PackageVerifier {
    PackageInformation verify(File packageDir, PackageDef packageDefExtension);
}
