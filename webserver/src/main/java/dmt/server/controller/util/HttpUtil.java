package dmt.server.controller.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @author Marco Romagnolo
 */
public class HttpUtil {

    public static String toString(HttpServletRequest request) {
        String cookies = null;
        if (request.getCookies()!=null) {
            cookies = "{";
            int len = request.getCookies().length;
            for (int i=0; i<len; i++) {
                Cookie cookie = request.getCookies()[i];
                cookies += "[domain:" + cookie.getDomain() + ", name:" + cookie.getName() + ", value:" + cookie.getValue() + ", path:" + cookie.getPath() + ", secure:" + cookie.getSecure() + ", maxAge:" + cookie.getMaxAge() + ", version:" + cookie.getVersion() + ", comment:" + cookie.getComment() + "]";
                if (i>0 && i<len-1) cookies = ", ";
            }
            cookies += "}";
        }
        return "Parameters:" + request.getParameterMap() +
                ", Cookies:" + cookies + ", Method:" + request.getMethod() +
                ", URI:" + request.getRequestURI() + ", Character encoding:" + request.getCharacterEncoding() +
                ", Protocol:" + request.getProtocol() + ", Remote address:" + request.getRemoteAddr() +
                ", Request URL:" + request.getRequestURL() + ", Locale:" + request.getLocale().getDisplayName() +
                ", Headers:[" + getHeaders(request)+ "]";
    }

    /**
    public static String getData(HttpServletRequest request) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ( (line=reader.readLine()) != null ) {
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        } catch (IOException ignored) {}
        return null;
    }**/

    public static String getHeaders(HttpServletRequest request) {
        StringBuilder headers = new StringBuilder();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String value = request.getHeader(key);
            headers.append(key).append(":").append(value);
            if (headerNames.hasMoreElements()) headers.append(",");
        }
        return headers.toString();
    }

}
