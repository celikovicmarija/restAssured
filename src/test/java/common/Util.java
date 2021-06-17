package common;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class Util {

    public static String PROP_FILE = "C:\\Users\\m.celikovic\\Documents\\REST API Testing\\APIFramework\\RestAssured\\src\\test\\resources\\__files\\api.properties";

    public static String loadFile(URI uri) {
        try {
            return new String(Files.readAllBytes(Path.of(Util.class.getClassLoader().getResource(uri.toString()).toURI())));
        } catch (IOException | URISyntaxException e) {
            throw new IllegalArgumentException("Unable to lad a file", e);
        }
    }

    public static String getPropertyValue(String key) {
        Properties prop = new Properties();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(PROP_FILE);
            prop.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return prop.getProperty(key);
    }

    public static String getJsonPath(Response response, String key) {
        String resp = response.asString();
        JsonPath js = new JsonPath(resp);
        return js.get(key).toString();
    }


}
