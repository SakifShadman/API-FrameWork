package Resources;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.*;
import java.util.Properties;

public class Utils {

    public static RequestSpecification reqSpec;

    public RequestSpecification requestSpecification() {

        if(reqSpec==null) {
            PrintStream log = null;
            try {
                log = new PrintStream(new FileOutputStream("logging.txt"));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

            reqSpec = new RequestSpecBuilder()
                    .setBaseUri(getGlobalValue("baseUrl"))
                    .addQueryParam("key","qaclick123")
                    .setContentType(ContentType.JSON)
                    .addFilter(RequestLoggingFilter.logRequestTo(log))
                    .addFilter(ResponseLoggingFilter.logResponseTo(log))
                    .build();

            return reqSpec;
        }
        return reqSpec;
    }

    public String getGlobalValue(String key) {
        Properties prop = new Properties();

        try {
            FileInputStream file = new FileInputStream("src/test/java/Resources/Global.properties");
            prop.load(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return prop.getProperty(key);
    }

    public String getJsonPath(Response response, String key) {
        String res = response.asString();
        JsonPath js = new JsonPath(res);
        return js.get(key);
    }
}
