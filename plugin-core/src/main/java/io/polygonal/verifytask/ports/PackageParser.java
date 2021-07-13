package io.polygonal.verifytask.ports;

import io.polygonal.verifytask.dto.PackageInformation;

import java.io.File;

public interface PackageParser {
    PackageInformation parseDirectory(File packageDir);
}
