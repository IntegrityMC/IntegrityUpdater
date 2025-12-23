package com.github.integrityupdate;

import lombok.Getter;

import java.util.List;

@Getter
public class PluginInfo {
    private final String name;
    private final String version;
    private final String mainLink;
    private final List<String> description;

    public PluginInfo(String name, String version, String mainLink, List<String> description) {
        this.name = name;
        this.version = version;
        this.mainLink = mainLink;
        this.description = description;
    }
}
