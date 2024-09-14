package inno_x_clients.x_clients.ext;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class EnvProperties {

    public static String getEnvProperties(String property) throws IOException {
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String appConfigPath = rootPath + "env.properties";

        Properties properties = new Properties();
        properties.load(new FileInputStream(appConfigPath));
        return properties.getProperty(property);
    }
}
