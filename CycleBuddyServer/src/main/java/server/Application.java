package server;

import java.io.FileInputStream;
import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static Properties serverConfiguration;

	public static void main(String[] args) {
		try {
			String configFilePath = "C:/Users/Colman/Documents/CycleBuddyServer/serversettings.properties";
			Properties propertiesFromConfigFile = new Properties();
			propertiesFromConfigFile.load(new FileInputStream(configFilePath));

			serverConfiguration = propertiesFromConfigFile;

			SpringApplication.run(Application.class, args);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
