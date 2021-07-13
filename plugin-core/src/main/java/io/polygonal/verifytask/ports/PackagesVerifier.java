package io.polygonal.verifytask.ports;

import io.polygonal.plugin.PackageDef;

import java.io.File;
import java.util.List;

public interface PackagesVerifier {
    /**
     * Verify all packages in base package directory.
     *
     * @param basePackageDir base package directory, not null
     * @param defExtensions  result set of extensions, not null
     */
    void verify(File basePackageDir, List<PackageDef> defExtensions);
}
