package rest.cucumber.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class PropertiesUtils {

    public final Logger logger = LogManager.getLogger(PropertiesUtils.class);
    private final Path filePath;
    private Properties props;

    public PropertiesUtils(Path filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads entire properties into system properties.
     *
     * @param path
     */
    public static void loadAsSystemProperties(Path path) {
        try {
            Properties properties = new Properties();
            properties.load(Files.newInputStream(path));
            properties.forEach((key, value) -> System.setProperty(key.toString(), value.toString()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static PropertiesUtils getPropertyFileReader(String fileName) {
        if (fileName.isBlank()) {
            throw new RuntimeException("Property File Name Should Not Be Blank");
        } else if (!fileName.endsWith(".properties")) {
            fileName = fileName + ".properties";
        }
        return new PropertiesUtils(Path.of(fileName));
    }

    public String getValue(String key) {
        try {
            if (props == null) {
                props = new Properties();
                props.load(Files.newInputStream(filePath));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return props.getProperty(key);
    }
}
