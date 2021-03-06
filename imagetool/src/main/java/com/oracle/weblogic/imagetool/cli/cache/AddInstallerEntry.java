// Copyright (c) 2019, 2021, Oracle and/or its affiliates.
// Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl.

package com.oracle.weblogic.imagetool.cli.cache;

import java.nio.file.Files;
import java.nio.file.Path;

import com.oracle.weblogic.imagetool.api.model.CommandResponse;
import com.oracle.weblogic.imagetool.cachestore.CacheStore;
import com.oracle.weblogic.imagetool.cachestore.CacheStoreException;
import com.oracle.weblogic.imagetool.installer.InstallerType;
import com.oracle.weblogic.imagetool.util.Utils;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import static com.oracle.weblogic.imagetool.cachestore.CacheStoreFactory.cache;

@Command(
        name = "addInstaller",
        description = "Add cache entry for wls, fmw, jdk or wdt installer",
        sortOptions = false
)
public class AddInstallerEntry extends CacheOperation {

    @Override
    public CommandResponse call() throws CacheStoreException {
        if (location != null && Files.isRegularFile(location) && !Utils.isEmptyString(version)) {
            String key = String.format("%s%s%s", type, CacheStore.CACHE_KEY_SEPARATOR, version);
            if (cache().getValueFromCache(key) != null) {
                return new CommandResponse(-1, "IMG-0048", key, cache().getValueFromCache(key));
            }
            cache().addToCache(key, location.toAbsolutePath().toString());
            return new CommandResponse(0, "IMG-0050", key, cache().getValueFromCache(key));
        }
        return new CommandResponse(-1, "IMG-0049", location);
    }

    @Option(
            names = {"--type"},
            description = "Type of installer. Valid values: ${COMPLETION-CANDIDATES}",
            required = true,
            defaultValue = "wls"
    )
    private InstallerType type;

    @Option(
            names = {"--version"},
            description = "Installer version. Ex: For WLS|FMW use 12.2.1.3.0 For jdk, use 8u201",
            required = true
    )
    private String version;

    @Option(
            names = {"--path"},
            description = "Location on disk. For ex: /path/to/FMW/installer.zip",
            required = true
    )
    private Path location;
}
