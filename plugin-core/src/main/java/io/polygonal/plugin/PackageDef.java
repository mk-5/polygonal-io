package io.polygonal.plugin;

import lombok.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = "name")
public class PackageDef {
    /**
     * The name of package / optional
     */
    String name = "";

    /**
     * If equals true, error will be thrown when package is missing
     */
    boolean required = false;

    /**
     * How many public scope object are allowed.
     * -1      | unlimited
     * 0      | 0 objects are allowed
     * n &gt; 0  | n objects are allowed
     */
    long publicScope = 0;

    /**
     * How many package-private scope object are allowed. Unlimited on root level by default.
     * -1      | unlimited
     * 0      | 0 objects are allowed
     * n &gt; 0  | n objects are allowed
     */
    long packagePrivateScope = 0;

    /**
     * How many protected scope object are allowed.
     * -1      | unlimited
     * 0      | 0 objects are allowed
     * n &gt; 0  | n objects are allowed
     */
    long protectedScope = 0;

    /**
     * How many internal scope object are allowed. (kotlin)
     * -1      | unlimited
     * 0      | 0 objects are allowed
     * n &gt; 0  | n objects are allowed
     */
    long internalScope = 0;

    /**
     * Allowed types
     */
    Set<String> types = new HashSet<>(Arrays.asList(
            ObjectType.INTERFACE.lowerCaseName(),
            ObjectType.ABSTRACT_CLASS.lowerCaseName(),
            ObjectType.CLASS.lowerCaseName(),
            ObjectType.ENUM.lowerCaseName(),
            ObjectType.OPEN_CLASS.lowerCaseName(),
            ObjectType.DATA_CLASS.lowerCaseName()
    ));

    public PackageDef(String name) {
        this.name = name;
    }
}
