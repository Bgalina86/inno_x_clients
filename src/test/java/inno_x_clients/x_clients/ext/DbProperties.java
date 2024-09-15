package inno_x_clients.x_clients.ext;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DbProperties {

    public static String getProperties(String property) throws IOException {
        String appConfigPath = "src/test/resources/conf.properties";

        Properties properties = new Properties();
        properties.load(new FileInputStream(appConfigPath));
        return properties.getProperty(property);
    }
}
