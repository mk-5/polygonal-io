package io.polygonal.verifytask;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PackageInformation {
    private int publicObjects;
    private int packagePrivateObjects;
    private int protectedObjects;
    private int internalObjects;
    private int interfaces;
    private int classes;
    private int dataClasses;
    private int openClasses;
    private int enums;
    private int abstractClasses;

    public void incPublicObjects() {
        publicObjects++;
    }

    public void incPackagePrivateObjects() {
        packagePrivateObjects++;
    }

    public void incProtectedObjects() {
        protectedObjects++;
    }

    public void incInternalObjects() {
        internalObjects++;
    }

    public void incInterfaces() {
        interfaces++;
    }

    public void incClasses() {
        classes++;
    }

    public void incDataClasses() {
        dataClasses++;
    }

    public void incOpenClasses() {
        openClasses++;
    }

    public void incEnums() {
        enums++;
    }

    public void incAbstractClasses() {
        abstractClasses++;
    }
}
