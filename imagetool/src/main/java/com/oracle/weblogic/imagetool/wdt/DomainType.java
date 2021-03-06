// Copyright (c) 2019, 2021, Oracle and/or its affiliates.
// Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl.

package com.oracle.weblogic.imagetool.wdt;

import com.oracle.weblogic.imagetool.installer.FmwInstallerType;

/**
 * Supported domain type. wls, jrf or restricted jrf.
 */
public enum DomainType {
    WLS("wls", FmwInstallerType.WLS),
    JRF("jrf", FmwInstallerType.FMW),
    RestrictedJRF("rjrf", FmwInstallerType.FMW);

    private String value;
    private FmwInstallerType installer;

    DomainType(String value, FmwInstallerType installer) {
        this.value = value;
        this.installer = installer;
    }

    /**
     * Create the Domain type Enum from the String value.
     * @param value the domain type string, ignoring case.
     * @return the enum domain type.
     */
    public static DomainType fromValue(String value) {
        for (DomainType eachType : DomainType.values()) {
            if (eachType.value.equalsIgnoreCase(value)) {
                return eachType;
            }
        }
        throw new IllegalArgumentException("argument " + value + " does not match any DomainType");
    }

    public FmwInstallerType installerType() {
        return installer;
    }
}

