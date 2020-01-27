package pl.mk5.polygonal

enum Message {
    // Errors
    POLYGON_OR_TEMPLATE_REQUIRED("You need to configure one of the following properties: polygonalArchitecture#polygon, polygonalArchitecture#polygonTemplate"),
    NOT_KEYWORD_FIELD_SHOULD_BE_MAP("Field '%s' suppose to be a package definition but it's not"),
    TYPES_INVALID("'%s' is not a valid type. Following types are allowed: %s"),
    PACKAGE_REQUIRED("Package '%s' has been marked as required, and it doesn't exist here: %s"),
    PUBLIC_OBJECTS_ERROR("%d public scope objects are allowed in '%s' package"),
    PACKAGE_PRIVATE_OBJECTS_ERROR("%d package-private scope objects are allowed in '%s' package"),
    PROTECTED_OBJECTS_ERROR("%d protected scope objects are allowed in '%s' package"),
    INTERFACES_ERROR("interfaces are not allowed in '%s' package"),
    CLASSES_ERROR("classes are not allowed in '%s' package"),
    ABSTRACT_CLASSES_ERROR("abstract classes are not allowed in '%s' package"),
    ENUMS_ERROR("enums are not allowed in '%s' package"),
    CHECK_TASK_NOT_FOUND("'check' task have not been found for module %s"),
    BASE_PACKAGE_DOESNT_EXIST("Given base package '%s' doesn't exist"),
    YML_TEMPLATE_NOT_FOUND("Given yml template doesn't exist or it's not readable"),
    TEMPLATE_IS_NOT_YML_FILE("Given template '%s' is not a yml file"),
    TEMPLATE_CANNOT_FIND_POLYGON("Cannot find '%s' definition in '%s'"),
    TEMPLATE_PACKAGES_SHOULD_CONTAIN_PACKAGES_DEFINITIONS("'packages' field should contain packages definitions"),
    // Console
    CHECKING_POLYGONS("Checking polygons for project %s"),
    CHECK_POLYGON("-- polygon %s is going to be checked");

    String msg;

    Message(String msg) {
        this.msg = msg
    }

    String msg() {
        return msg
    }

    String withArgs(Object... args) {
        return sprintf(this.msg, args)
    }

    @Override
    String toString() {
        return msg
    }
}
