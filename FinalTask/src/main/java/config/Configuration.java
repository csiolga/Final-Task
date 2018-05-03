package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {

    private static final InputStream CONFIG = Configuration.class.getResourceAsStream("/config.properties");
    private static final Properties PROPS;

    static {
        PROPS = new Properties();
        try {
            PROPS.load(CONFIG);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getValue(String key) {
        return PROPS.getProperty(key);
    }
}
