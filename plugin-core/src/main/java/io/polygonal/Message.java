package io.polygonal;

import java.text.MessageFormat;

public enum Message {
    POLYGON_OR_TEMPLATE_REQUIRED("You need to configure one of the following properties: polygonalArchitecture#polygon, polygonalArchitecture#polygonTemplate or just create polygon.yml"),
    NOT_KEYWORD_FIELD_SHOULD_BE_MAP("Field ''{0}'' suppose to be a package definition but it''s not"),
    TYPES_INVALID("''{0}'' is not a valid type. Following types are allowed: {1}"),
    TYPES_FORMAT_INVALID("Types fields suppose to be a Set<String>."),
    PACKAGE_REQUIRED("Package ''{0}'' has been marked as required, and it doesn''t exist here: {1}"),
    PUBLIC_OBJECTS_ERROR("''{1}'' package may contain up to {0} public objects"),
    PACKAGE_PRIVATE_OBJECTS_ERROR("''{1}'' package may contain up to {0} package-private objects"),
    PROTECTED_OBJECTS_ERROR("''{1}'' package may contain up to {0} protected objects"),
    INTERNAL_OBJECTS_ERROR("''{1}'' package may contain up to {0} internal objects"),
    INTERFACES_ERROR("''{0}'' package shouldn''t contain interfaces"),
    CLASSES_ERROR("''{0}'' package shouldn''t contain classes"),
    DATA_CLASSES_ERROR("''{0}'' package shouldn''t contain data classes"),
    OPEN_CLASSES_ERROR("''{0}'' package shouldn''t contain open classes"),
    ABSTRACT_CLASSES_ERROR("''{0}'' package shouldn''t contain abstract classes"),
    ENUMS_ERROR("''{0}'' package must shouldn''t enums"),
    CHECK_TASK_NOT_FOUND("''check'' task have not been found for module {0}"),
    BASE_PACKAGE_DOESNT_EXIST("Base package ''{0}'' doesn''t exist"),
    YML_TEMPLATE_NOT_FOUND("''{0}'' yml template doesn''t exist"),
    USING_TEMPLATE("Using polygon template ''{0}''"),
    TEMPLATE_IS_NOT_YML_FILE("''{0}'' is not valid YML file"),
    TEMPLATE_CANNOT_FIND_POLYGON("Cannot find ''{0}'' definition in ''{1}''"),
    TEMPLATE_PACKAGES_SHOULD_CONTAIN_PACKAGES_DEFINITIONS("''packages'' field should contain packages definitions"),
    CANNOT_RECOGNIZE_LANGUAGE("JVM language for module ''{0}'' couldn''t be recognize for. Please check if Java, or Kotlin plugin has been applied."),
    CHECKING_POLYGONS("> checking polygons for project ''{0}''"),
    CHECK_POLYGON("> polygon ''{0}''..."),
    PROJECT_REQUIRED("Project cannot be null");

    Message(String msg) {
        this.msg = msg;
    }

    public String msg() {
        return msg;
    }

    public String withArgs(Object... args) {
        if (msg.contains("must not contain") && "".equals(args[0])) {
            args[0] = "root";
        } else if (msg.contains("may contain up to") && "".equals(args[1])) {
            args[1] = "root";
        }
        return MessageFormat.format(msg, args);
    }

    @Override
    public String toString() {
        return msg;
    }

    private String msg;
}
