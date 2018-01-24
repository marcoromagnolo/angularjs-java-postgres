package dmt.server.component;

import org.springframework.stereotype.Component;

/**
 * @author Marco Romagnolo
 */
@Component
public class Network {

    public Network(String proxyHost, String proxyPort, String proxyUsername, String proxyPassword) {
        System.getProperties().put("http.proxyHost", proxyHost);
        System.getProperties().put("http.proxyPort", proxyPort);
        System.getProperties().put("http.proxyUser", proxyUsername);
        System.getProperties().put("http.proxyPassword", proxyPassword);
    }
}
