package server;

import java.io.FileInputStream;
import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = { "server"} )
@SpringBootApplication
public class Application
{

    // Used to allow the contents of a config file to be read by all classes.
    // This is a horrible hack and needs to be refactored.
    public static Properties config;

    public static void main(String[] args)
    {
        try
        {
            // This hard-coded path for the config file also needs to be changed maybe pass the path as a an argument to main.
            String configFilePath = "C:/Users/Colman/Documents/CycleBuddyServer/serversettings.properties";
            Properties propertiesFromConfigFile = new Properties();
            propertiesFromConfigFile.load(new FileInputStream(configFilePath));
            config = propertiesFromConfigFile;

            // This is where the server application is actually launched.
            SpringApplication.run(Application.class, args);
        } 
        
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
