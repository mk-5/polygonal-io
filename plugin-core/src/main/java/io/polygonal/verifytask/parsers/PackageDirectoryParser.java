package io.polygonal.verifytask.parsers;

import com.google.inject.Inject;
import io.polygonal.Message;
import io.polygonal.Logger;
import io.polygonal.verifytask.dto.PackageInformation;
import io.polygonal.verifytask.dto.ProjectLanguage;
import io.polygonal.verifytask.ports.PackageParser;
import io.polygonal.verifytask.ports.SourceCodeParser;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Set;

import static io.polygonal.Logger.log;

class PackageDirectoryParser implements PackageParser {
    private static final String SOURCE_CODE_PARSER_ERROR = "";
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
            log.debug(Message.PARSING_SOURCE_CODE_FOR_FILE.withArgs(path.getFileName()));
            try (BufferedReader reader = Files.newBufferedReader(path)) {
                sourceCodeParser.processSingleFile(reader, information);
            } catch (IOException e) {
                log.error(Message.SOURCE_CODE_PARSER_ERROR.withArgs(path.getFileName()), e);
            }
        });
        return information;
    }
}
