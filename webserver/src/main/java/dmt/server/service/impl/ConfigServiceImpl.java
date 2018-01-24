package dmt.server.service.impl;

import dmt.server.service.ConfigService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Marco Romagnolo
 */
@Service("configService")
public class ConfigServiceImpl implements ConfigService {

    @Value("${name}")
    private String name;

    @Value("${description}")
    private String description;

    @Value("${version}")
    private String version;

    @Override
    public Map<String, Object> getConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put("name", name);
        config.put("version", version);
        Map<String, Object> url = new HashMap<>();
        config.put("url", url);
        url.put("login", "/login");
        url.put("logout", "/logout");
        url.put("register", "/register");
        url.put("confirm", "/confirm");
        url.put("recovery", "/recovery");
        url.put("reset", "/reset");
        return config;
    }

}
