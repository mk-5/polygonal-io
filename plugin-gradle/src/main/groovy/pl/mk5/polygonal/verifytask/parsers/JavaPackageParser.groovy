package pl.mk5.polygonal.verifytask.parsers

import groovy.io.FileType
import groovy.transform.PackageScope
import pl.mk5.polygonal.verifytask.models.PackageInformation

import java.util.regex.Pattern

@PackageScope
class JavaPackageParser implements PackageParser {
    static final String ALL_TYPES = "(class|interface|abstract class|enum)\\s*"
    static final String ALL_SCOPES = "(public|protected|[a]*)"

    static final Pattern PUBLIC_OBJECTS_PATTERN = Pattern.compile("^public ${ALL_TYPES}", Pattern.MULTILINE)
    static final Pattern PROTECTED_OBJECTS_PATTERN = Pattern.compile("^protected ${ALL_TYPES}", Pattern.MULTILINE)
    static final Pattern CLASSES_PATTERN = Pattern.compile("^${ALL_SCOPES}\\s*class", Pattern.MULTILINE)
    static final Pattern INTERFACES_PATTERN = Pattern.compile("^${ALL_SCOPES}\\s*interface", Pattern.MULTILINE)
    static final Pattern ABSTRACT_CLASS_PATTERN = Pattern.compile("^${ALL_SCOPES}\\s*abstract class", Pattern.MULTILINE)
    static final Pattern ENUM_PATTERN = Pattern.compile("^${ALL_SCOPES}\\s*enum", Pattern.MULTILINE)

    @Override
    PackageInformation parse(File packageDir) {
        def information = new PackageInformation()
        packageDir.eachFile(FileType.FILES, { file ->
            def text = file.text.trim()
            if (PUBLIC_OBJECTS_PATTERN.matcher(text).find()) {
                information.publicObjects++
            } else if (PROTECTED_OBJECTS_PATTERN.matcher(text).find()) {
                information.protectedObjects++
            } else {
                information.packagePrivateObjects++
            }
            if (CLASSES_PATTERN.matcher(text).find()) {
                information.classes++
            }
            if (INTERFACES_PATTERN.matcher(text).find()) {
                information.interfaces++
            }
            if (ABSTRACT_CLASS_PATTERN.matcher(text).find()) {
                information.abstractClasses++
            }
            if (ENUM_PATTERN.matcher(text).find()) {
                information.enums++
            }
        })
        return information
    }
}
