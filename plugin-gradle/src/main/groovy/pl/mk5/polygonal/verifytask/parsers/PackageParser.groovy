package pl.mk5.polygonal.verifytask.parsers

import pl.mk5.polygonal.verifytask.models.PackageInformation

interface PackageParser {
    PackageInformation parse(File packageDir)
}