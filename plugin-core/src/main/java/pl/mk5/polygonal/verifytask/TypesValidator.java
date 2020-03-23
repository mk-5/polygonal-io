package pl.mk5.polygonal.verifytask;

import java.util.Set;

import pl.mk5.polygonal.Message;
import pl.mk5.polygonal.plugin.PackageDef;

class TypesValidator {
    private static final PackageDef emptyPackageDef = new PackageDef();

    @SuppressWarnings("unchecked")
    static void validate(Object types) {
        Conditions.check(types instanceof Set, Message.TYPES_FORMAT_INVALID.msg());
        ((Set<String>) types).forEach(type -> {
            Conditions.check(emptyPackageDef.getTypes().contains(type), Message.TYPES_INVALID.withArgs(type, emptyPackageDef.getTypes().toString()));
        });
    }
}
