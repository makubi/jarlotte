package de.metacoder.jarlotte.api;

import java.io.File;
import java.util.Map;

public class JarlotteContext {
    private final File webappDir;
    private final Map<String, String> properties;

    public JarlotteContext(File webappDir, Map<String, String> properties) {
        this.webappDir = webappDir;
        this.properties = properties;
    }

    public File getWebappDir() {
        return webappDir;
    }

    public Map<String, String> getProperties() {
        return properties;
    }
}
