package pl.mk5.polygonal.verifytask

class Conditions {
    static void check(boolean condition, String message) {
        if (!condition) {
            throw new ConditionException(message)
        }
    }
}
