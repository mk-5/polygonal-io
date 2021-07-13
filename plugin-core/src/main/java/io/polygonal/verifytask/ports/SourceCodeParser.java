package io.polygonal.verifytask.ports;

import io.polygonal.verifytask.dto.PackageInformation;

import java.io.BufferedReader;

public interface SourceCodeParser extends LanguageAware {
    /**
     * Parse file read in {@code reader}, and put result into {@code resultInformation}
     *
     * @param reader            source code file reader, not null
     * @param resultInformation resultInformation about package, not null
     */
    void processSingleFile(BufferedReader reader, PackageInformation resultInformation);
}
