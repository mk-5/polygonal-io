package io.polygonal.verifytask.parsers;

import io.polygonal.verifytask.PackageInformation;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.util.regex.Pattern;

class JavaPackageParser extends PackageParserProcessor {

    private static final String ALL_TYPES = "(class|interface|abstract class|enum)\\s*";
    private static final String ALL_SCOPES = "(public|protected|[a]*)";

    private static final Pattern PUBLIC_OBJECTS_PATTERN = Pattern.compile("^public " + ALL_TYPES);
    private static final Pattern PROTECTED_OBJECTS_PATTERN = Pattern.compile("^protected " + ALL_TYPES);
    private static final Pattern CLASSES_PATTERN = Pattern.compile("^" + ALL_SCOPES + "\\s*class");
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
                if (PUBLIC_OBJECTS_PATTERN.matcher(text).find()) {
                    information.incPublicObjects();
                    scopeFound = true;
                } else if (PROTECTED_OBJECTS_PATTERN.matcher(text).find()) {
                    information.incProtectedObjects();
                    scopeFound = true;
                } else if (typeFound) {
                    information.incPackagePrivateObjects();
                    scopeFound = true;
                }
            }
            if (!typeFound) {
                if (CLASSES_PATTERN.matcher(text).find()) {
                    information.incClasses();
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
