package sylvain.juge.googlecalendar;

import java.awt.Desktop;
import java.awt.Desktop.Action;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.*;

public class AuthTest {

    private static final String AUTH_URL = "https://accounts.google.com/o/oauth2/auth";
    private static final String TOKEN_URL = "https://accounts.google.com/o/oauth2/token";
    private static final String CLIENT_ID = "363417282032.apps.googleusercontent.com";
    private static final String REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob";
    private static final String CLIENT_SECRET = "WtKa_st884CF2JKAt0KJILlW";

    private static final String SCOPE = "https://www.googleapis.com/auth/calendar";

    @Test
    public void testSignup() throws IOException, URISyntaxException {

        URI codeUri = new URIBuilder(AUTH_URL)
                .addParameter("response_type", "code")
                .addParameter("client_id", CLIENT_ID)
                .addParameter("redirect_uri", REDIRECT_URI)
                .addParameter("scope", SCOPE)
                .build();

        boolean browser = false;
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            if (desktop.isSupported(Action.BROWSE)) {
                desktop.browse(codeUri);
                browser = true;
            }
        }
        if (!browser) {
            System.out.println("open this url in your browser");
            System.out.println(codeUri);
        }

        // TODO : retrieve code through local server (if possible)
        // or find a way to control opened browser window

        System.out.println("Type the code you received here: ");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String authorizationCode = in.readLine();

        URI tokenUri = new URIBuilder(TOKEN_URL).build();

        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        formparams.add(new BasicNameValuePair("code", authorizationCode));
        formparams.add(new BasicNameValuePair("client_id", CLIENT_ID));
        formparams.add(new BasicNameValuePair("client_secret", CLIENT_SECRET));
        formparams.add(new BasicNameValuePair("redirect_uri", REDIRECT_URI));
        formparams.add(new BasicNameValuePair("grant_type", "authorization_code"));

        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(tokenUri);
        post.setEntity(new UrlEncodedFormEntity(formparams, "UTF-8"));
        HttpResponse response = client.execute(post);
        
        // TODO : refresh token when needed

        String responseString = IOUtils.toString(response.getEntity().getContent());
        
        System.out.println(responseString);

        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(200);
    }
}
