package pl.mk5.polygonal.verifytask.parsers

import groovy.io.FileType
import groovy.transform.PackageScope
import pl.mk5.polygonal.verifytask.models.PackageInformation

import java.util.regex.Pattern

@PackageScope
class JavaPackageParser implements PackageParser {
    static final String ALL_TYPES = "(class|interface|abstract class|enum)\\s*"
    static final String ALL_SCOPES = "(public|protected|[a]*)"

    static final Pattern PUBLIC_OBJECTS_PATTERN = Pattern.compile("^public ${ALL_TYPES}")
    static final Pattern PROTECTED_OBJECTS_PATTERN = Pattern.compile("^protected ${ALL_TYPES}")
    static final Pattern CLASSES_PATTERN = Pattern.compile("^${ALL_SCOPES}\\s*class")
    static final Pattern INTERFACES_PATTERN = Pattern.compile("^${ALL_SCOPES}\\s*interface")
    static final Pattern ABSTRACT_CLASS_PATTERN = Pattern.compile("^${ALL_SCOPES}\\s*abstract class")
    static final Pattern ENUM_PATTERN = Pattern.compile("^${ALL_SCOPES}\\s*enum")

    @Override
    PackageInformation parse(File packageDir) {
        def information = new PackageInformation()
        packageDir.eachFile(FileType.FILES, { file ->
            file.withReader { reader ->
                def text = '', scopeFound = false, typeFound = false;
                while ((text = reader.readLine()) != null) {
                    if (!scopeFound) {
                        if (PUBLIC_OBJECTS_PATTERN.matcher(text).find()) {
                            information.publicObjects++
                            scopeFound = true
                        } else if (PROTECTED_OBJECTS_PATTERN.matcher(text).find()) {
                            information.protectedObjects++
                            scopeFound = true
                        } else if (typeFound) {
                            information.packagePrivateObjects++
                            scopeFound = true
                        }
                    }
                    if (!typeFound) {
                        if (CLASSES_PATTERN.matcher(text).find()) {
                            information.classes++
                            typeFound = true
                        }
                        if (INTERFACES_PATTERN.matcher(text).find()) {
                            information.interfaces++
                            typeFound = true
                        }
                        if (ABSTRACT_CLASS_PATTERN.matcher(text).find()) {
                            information.abstractClasses++
                            typeFound = true
                        }
                        if (ENUM_PATTERN.matcher(text).find()) {
                            information.enums++
                            typeFound = true
                        }
                    }
                    if (scopeFound && typeFound) {
                        break;
                    }
                }
            }
            return;
        })
        return information
    }
}
