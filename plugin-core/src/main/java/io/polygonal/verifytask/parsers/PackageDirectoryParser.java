package io.polygonal.verifytask.parsers;

import com.google.inject.Inject;
import io.polygonal.verifytask.dto.ProjectLanguage;
import io.polygonal.verifytask.dto.PackageInformation;
import io.polygonal.verifytask.ports.PackageParser;
import io.polygonal.verifytask.ports.SourceCodeParser;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Set;

class PackageDirectoryParser implements PackageParser {
    private final SourceCodeParser sourceCodeParser;

    @Inject
    public PackageDirectoryParser(ProjectLanguage projectLanguage, Set<SourceCodeParser> parsers) {
        sourceCodeParser = parsers.stream()
                .filter(parser -> parser.test(projectLanguage.getLanguage()))
                .findFirst().orElseThrow(IllegalStateException::new);
    }

    @SneakyThrows
    public PackageInformation parseDirectory(File packageDir) {
        PackageInformation information = new PackageInformation();
        Files.list(packageDir.toPath()).filter(Files::isRegularFile).forEach(path -> {
            try (BufferedReader reader = Files.newBufferedReader(path)) {
                sourceCodeParser.processSingleFile(reader, information);
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        });
        return information;
    }
}
