package pl.mk5.polygonal.verifytask.parsers;

import java.io.File;

import pl.mk5.polygonal.verifytask.PackageInformation;

public interface PackageParser {
    PackageInformation parse(File packageDir);
}
