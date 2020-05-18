package framework;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private final String propertiesFileName;
    private Properties properties;

    /**
     * Constructor. Reads and remembers the properties in the file
     * So you will want one of these for each different properties file that you use
     *
     * @param relativePath - relative to the root of the project
     *                     - eg configuration/systemUnderTest.properties
     * @throws IOException - if the given file can't be opened, or read
     */
    public ConfigReader(String relativePath) throws IOException {
        propertiesFileName = relativePath;
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(relativePath));
            properties = new Properties();
            try {
                properties.load(reader);
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new IOException("Failed to understand the properties file at:" + relativePath + ":");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new IOException("Failed to find properties file at:" + relativePath + ":");
        }
    }

    /**
     * get the configure value for this key. To use this successfully, you will need to know the contents of the relevant properties file
     *
     * @param name - name e.g. uiDomainName
     * @return - the value for that name
     * @throws NoSuchFieldException - if the given name is not found in the config file
     */
    public String getProperty(String name) throws NoSuchFieldException {
        String value = properties.getProperty(name);
        if (value != null) return value;
        else
            throw new NoSuchFieldException("There was no property called :" + name + ": in the properties file :" + propertiesFileName + ":");
    }
}
