package sylvain.juge.googlecalendar;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.Calendar;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.fest.assertions.api.Assertions.assertThat;

public class AuthTestWithLibrary {

    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();
    public static final String CLIENT_SECRETS = "/client_secrets.json";

    @Test
    public void test() {


        Calendar client;

        Credential credential = authorize();

        // TODO : use calendar client

        assertThat(credential).isNotNull();


    }

    private Credential authorize(){
        try (InputStream input = AuthTestWithLibrary.class.getResourceAsStream(CLIENT_SECRETS)){
            GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,input);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }
}
