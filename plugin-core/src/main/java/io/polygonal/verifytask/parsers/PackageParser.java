package io.polygonal.verifytask.parsers;

import io.polygonal.verifytask.PackageInformation;

import java.io.File;

public interface PackageParser {
    PackageInformation parse(File packageDir);
}
