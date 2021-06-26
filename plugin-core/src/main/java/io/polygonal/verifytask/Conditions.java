package io.polygonal.verifytask;

public class Conditions {
    private Conditions() {
    }

    public static void check(boolean condition, String message) {
        if (!condition) {
            throw new ConditionException(message);
        }
    }
}
