package io.polygonal.verifytask;

import io.polygonal.Message;
import io.polygonal.plugin.PackageDef;

import java.util.Set;

/**
 * Validates if types are valid {@link PackageDef#types}.
 */
class TypesValidator {
    private static final PackageDef emptyPackageDef = new PackageDef();

    private TypesValidator() {
    }

    @SuppressWarnings("unchecked")
    static void validate(Object types) {
        Conditions.check(types instanceof Set, Message.TYPES_FORMAT_INVALID.msg());
        ((Set<String>) types).forEach(type -> {
            Conditions.check(emptyPackageDef.getTypes().contains(type), Message.TYPES_INVALID.withArgs(type, emptyPackageDef.getTypes().toString()));
        });
    }
}
