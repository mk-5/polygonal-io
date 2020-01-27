package pl.mk5.polygonal.verifytask

import groovy.transform.PackageScope

@PackageScope
class ConditionException extends RuntimeException {
    ConditionException(String message) {
        super(message)
    }
}
