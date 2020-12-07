package fr.chaffotm.gamebook.elementary.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class IOService {

    public InputStream getInputStream(String location) throws IOException {
        final String protocol = location.replaceFirst("^(\\w+):.+$", "$1").toLowerCase();
        if ("file".equals(protocol)) {
            return new URL(location).openStream();
        } else if ("classpath".equals(protocol)) {
            final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            return classLoader.getResourceAsStream(location.replaceFirst("^\\w+:", ""));
        } else {
            throw new IOException("Unsupported protocol in path '\"" + location + "\"'");
        }
    }
}
