package io.polygonal.verifytask.parsers;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import io.polygonal.verifytask.PackageInformation;
import lombok.SneakyThrows;

abstract class PackageObjectsProcessor implements PackageParser {

    @Override
    @SneakyThrows
    public PackageInformation parse(File packageDir) {
        PackageInformation information = new PackageInformation();
        Files.list(packageDir.toPath()).filter(Files::isRegularFile).forEach(path -> {
            try (BufferedReader reader = Files.newBufferedReader(path)) {
                processSingleFile(reader, information);
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        });
        return information;
    }

    protected abstract void processSingleFile(BufferedReader reader, PackageInformation information);
}
