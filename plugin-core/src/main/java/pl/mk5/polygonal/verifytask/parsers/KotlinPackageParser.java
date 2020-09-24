package pl.mk5.polygonal.verifytask.parsers;

import java.io.BufferedReader;
import java.util.regex.Pattern;

import lombok.SneakyThrows;
import pl.mk5.polygonal.verifytask.PackageInformation;

class KotlinPackageParser extends PackageParserProcessor {

    private static final String ALL_TYPES = "(class|interface|abstract class|enum class|data class|open class)\\s*";
    private static final String ALL_SCOPES = "(private|internal|[a]*)";

    private static final Pattern INTERNAL_OBJECTS_PATTERN = Pattern.compile("^internal " + ALL_TYPES);
    private static final Pattern PRIVATE_OBJECTS_PATTERN = Pattern.compile("^private " + ALL_TYPES);
    private static final Pattern CLASSES_PATTERN = Pattern.compile("^" + ALL_SCOPES + "\\s*class");
    private static final Pattern DATA_CLASSES_PATTERN = Pattern.compile("^" + ALL_SCOPES + "\\s*data class");
    private static final Pattern OPEN_CLASSES_PATTERN = Pattern.compile("^" + ALL_SCOPES + "\\s*open class");
    private static final Pattern INTERFACES_PATTERN = Pattern.compile("^" + ALL_SCOPES + "\\s*interface");
    private static final Pattern ABSTRACT_CLASS_PATTERN = Pattern.compile("^" + ALL_SCOPES + "\\s*abstract class");
    private static final Pattern ENUM_PATTERN = Pattern.compile("^" + ALL_SCOPES + "\\s*enum");

    @Override
    @SneakyThrows
    protected void process(BufferedReader reader, PackageInformation information) {
        String text;
        boolean scopeFound = false, typeFound = false;
        while ((text = reader.readLine()) != null) {
            if (!scopeFound) {
                if (PRIVATE_OBJECTS_PATTERN.matcher(text).find()) {
                    // private doesn't matter
                } else if (INTERNAL_OBJECTS_PATTERN.matcher(text).find()) {
                    information.incInternalObjects();
                    scopeFound = true;
                } else if (typeFound) {
                    information.incPublicObjects();
                    scopeFound = true;
                }
            }
            if (!typeFound) {
                if (CLASSES_PATTERN.matcher(text).find()) {
                    information.incClasses();
                    typeFound = true;
                }
                if (DATA_CLASSES_PATTERN.matcher(text).find()) {
                    information.incDataClasses();
                    typeFound = true;
                }
                if (OPEN_CLASSES_PATTERN.matcher(text).find()) {
                    information.incOpenClasses();
                    typeFound = true;
                }
                if (INTERFACES_PATTERN.matcher(text).find()) {
                    information.incInterfaces();
                    typeFound = true;
                }
                if (ABSTRACT_CLASS_PATTERN.matcher(text).find()) {
                    information.incAbstractClasses();
                    typeFound = true;
                }
                if (ENUM_PATTERN.matcher(text).find()) {
                    information.incEnums();
                    typeFound = true;
                }
            }
            if (scopeFound && typeFound) {
                break;
            }
        }
    }
}
