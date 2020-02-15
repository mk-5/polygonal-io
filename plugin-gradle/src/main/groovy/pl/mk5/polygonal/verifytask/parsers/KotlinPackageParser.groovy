package pl.mk5.polygonal.verifytask.parsers

import groovy.io.FileType
import groovy.transform.PackageScope
import pl.mk5.polygonal.verifytask.models.PackageInformation

import java.util.regex.Pattern

@PackageScope
class KotlinPackageParser implements PackageParser {
    static final String ALL_TYPES = "(class|interface|abstract class|enum class|data class|open class)\\s*"
    static final String ALL_SCOPES = "(private|internal|[a]*)"

    static final Pattern INTERNAL_OBJECTS_PATTERN = Pattern.compile("^internal ${ALL_TYPES}")
    static final Pattern PRIVATE_OBJECTS_PATTERN = Pattern.compile("^private ${ALL_TYPES}")
    static final Pattern CLASSES_PATTERN = Pattern.compile("^${ALL_SCOPES}\\s*class")
    static final Pattern DATA_CLASSES_PATTERN = Pattern.compile("^${ALL_SCOPES}\\s*data class")
    static final Pattern OPEN_CLASSES_PATTERN = Pattern.compile("^${ALL_SCOPES}\\s*open class")
    static final Pattern INTERFACES_PATTERN = Pattern.compile("^${ALL_SCOPES}\\s*interface")
    static final Pattern ABSTRACT_CLASS_PATTERN = Pattern.compile("^${ALL_SCOPES}\\s*abstract class")
    static final Pattern ENUM_PATTERN = Pattern.compile("^${ALL_SCOPES}\\s*enum")

    @Override
    PackageInformation parse(File packageDir) {
        def information = new PackageInformation()
        packageDir.eachFile(FileType.FILES, { file ->
            def text = file.withReader { reader ->
                def text = '', scopeFound = false, typeFound = false;
                while ((text = reader.readLine()) != null) {
                    if (!scopeFound) {
                        if (PRIVATE_OBJECTS_PATTERN.matcher(text).find()) {
                            // private doesn't matter
                        } else if (INTERNAL_OBJECTS_PATTERN.matcher(text).find()) {
                            information.internalObjects++
                            scopeFound = true
                        } else if (typeFound) {
                            information.publicObjects++
                            scopeFound = true
                        }
                    }
                    if (!typeFound) {
                        if (CLASSES_PATTERN.matcher(text).find()) {
                            information.classes++
                            typeFound = true
                        }
                        if (DATA_CLASSES_PATTERN.matcher(text).find()) {
                            information.dataClasses++
                            typeFound = true
                        }
                        if (OPEN_CLASSES_PATTERN.matcher(text).find()) {
                            information.openClasses++
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
                return;
            }
        })
        return information
    }
}
