package pl.mk5.polygonal.verifytask

import groovy.transform.PackageScope

@PackageScope
class Conditions {
    static void check(boolean condition, String message) {
        if (!condition) {
            throw new ConditionException(message)
        }
    }
}
