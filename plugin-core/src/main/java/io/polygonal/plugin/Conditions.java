package io.polygonal.plugin;

public class Conditions {
    private Conditions() {
    }

    public static void check(boolean condition, String message) {
        if (!condition) {
            throw new ConditionException(message);
        }
    }
}
