package pl.mk5.polygonal.verifytask.verifiers

import pl.mk5.polygonal.plugin.PackageDefExtension
import pl.mk5.polygonal.verifytask.models.PackageInformation

interface PackageVerifier {
    PackageInformation verify(File packageDir, PackageDefExtension packageDefExtension)
}