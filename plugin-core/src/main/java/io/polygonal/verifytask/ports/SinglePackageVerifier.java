package io.polygonal.verifytask.ports;

import io.polygonal.plugin.PackageDef;
import io.polygonal.verifytask.dto.PackageInformation;

import java.io.File;

public interface SinglePackageVerifier extends LanguageAware {
    /**
     * Verify single package, and returns information about this package.
     *
     * @param packageDir package directory, not null
     * @param packageDef package definition, not null
     * @return information about package
     */
    PackageInformation verify(File packageDir, PackageDef packageDef);
}
