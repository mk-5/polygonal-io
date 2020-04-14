package pl.mk5.polygonal;

import java.text.MessageFormat;

public enum Message {
    POLYGON_OR_TEMPLATE_REQUIRED("You need to configure one of the following properties: polygonalArchitecture#polygon, polygonalArchitecture#polygonTemplate"),
    NOT_KEYWORD_FIELD_SHOULD_BE_MAP("Field ''{0}'' suppose to be a package definition but it''s not"),
    TYPES_INVALID("''{0}'' is not a valid type. Following types are allowed: {1}"),
    TYPES_FORMAT_INVALID("Types fields suppose to be a Set<String>."),
    PACKAGE_REQUIRED("Package ''{0}'' has been marked as required, and it doesn''t exist here: {1}"),
    PUBLIC_OBJECTS_ERROR("{0} public scope objects are allowed in ''{1}'' package"),
    PACKAGE_PRIVATE_OBJECTS_ERROR("{0} package-private scope objects are allowed in ''{1}'' package"),
    PROTECTED_OBJECTS_ERROR("{0} protected scope objects are allowed in ''{1}'' package"),
    INTERNAL_OBJECTS_ERROR("{0} internal scope objects are allowed in ''{1}'' package"),
    INTERFACES_ERROR("interfaces are not allowed in ''{0}'' package"),
    CLASSES_ERROR("classes are not allowed in ''{0}'' package"),
    DATA_CLASSES_ERROR("data classes are not allowed in ''{0}'' package"),
    OPEN_CLASSES_ERROR("open classes are not allowed in ''{0}'' package"),
    ABSTRACT_CLASSES_ERROR("abstract classes are not allowed in ''{0}'' package"),
    ENUMS_ERROR("enums are not allowed in ''{0}'' package"),
    CHECK_TASK_NOT_FOUND("''check'' task have not been found for module {0}"),
    BASE_PACKAGE_DOESNT_EXIST("Given base package ''{0}'' doesn''t exist"),
    YML_TEMPLATE_NOT_FOUND("Given yml template doesn''t exist or it''s not readable"),
    USING_TEMPLATE("Using polygon template ''{0}''"),
    TEMPLATE_IS_NOT_YML_FILE("Given template ''{0}'' is not a yml file"),
    TEMPLATE_CANNOT_FIND_POLYGON("Cannot find ''{0}'' definition in ''{1}''"),
    TEMPLATE_PACKAGES_SHOULD_CONTAIN_PACKAGES_DEFINITIONS("''packages'' field should contain packages definitions"),
    CANNOT_RECOGNIZE_LANGUAGE("JVM language for module ''{0}'' couldn''t be recognize for. Please check if Java, or Kotlin plugin has been applied."),
    CHECKING_POLYGONS("> checking polygons for project ''{0}''"),
    CHECK_POLYGON("> analyzing polygon ''{0}''..."),
    PROJECT_REQUIRED("Project cannot be null");

    Message(String msg) {
        this.msg = msg;
    }

    public String msg() {
        return msg;
    }

    public String withArgs(Object... args) {
        return MessageFormat.format(msg, args);
    }

    @Override
    public String toString() {
        return msg;
    }

    private String msg;
}
