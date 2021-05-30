package ua.lviv.iot.utils;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyValues {
    String result = "";
    InputStream inputStream;
    Properties properties;
    private String host;
    private String port;
    private String username;
    private String password;

    public PropertyValues(){
        try {

            properties = new Properties();
            String propFileName = "config.properties";
            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if(inputStream != null) {
                properties.load(inputStream);
            }else {
                throw new FileNotFoundException("property file with '" + propFileName + "' not found");
            }
            host = properties.getProperty("sql_host");
            port = properties.getProperty("cassandra_port");
            username = properties.getProperty("cassandra_username");
            password = properties.getProperty("sql_password");

        }catch (Exception e){
            System.err.println("Exception: " + e);
        }

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
