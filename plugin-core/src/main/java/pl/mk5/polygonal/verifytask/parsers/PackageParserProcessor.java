package pl.mk5.polygonal.verifytask.parsers;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import lombok.SneakyThrows;
import pl.mk5.polygonal.verifytask.PackageInformation;

abstract class PackageParserProcessor implements PackageParser {

    @Override
    @SneakyThrows
    public PackageInformation parse(File packageDir) {
        PackageInformation information = new PackageInformation();
        Files.list(packageDir.toPath()).filter(path -> Files.isRegularFile(path)).forEach(path -> {
            try (BufferedReader reader = Files.newBufferedReader(path)) {
                process(reader, information);
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        });
        return information;
    }

    protected abstract void process(BufferedReader reader, PackageInformation information);
}
