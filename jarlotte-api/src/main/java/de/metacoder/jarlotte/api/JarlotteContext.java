package de.metacoder.jarlotte.api;
import java.io.File;
import java.util.Map;
import java.util.Properties;

public class JarlotteContext {

    private final File contentDir;
    private final Properties properties;
    private final String[] runArgs;

    public JarlotteContext(File contentDir, Properties properties, String[] runArgs) {
        this.contentDir = contentDir;
        this.properties = properties;
        this.runArgs = runArgs;
    }

    public File getContentDir() {
        return contentDir;
    }

    public Properties getProperties() {
        return properties;
    }

    public String[] getRunArgs() {
        return runArgs;
    }
}