package fr.chaffotm.gamebook.elementary.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class IOService {

    public List<Path> getStoryPaths(String folder) throws IOException, URISyntaxException {
        final String protocol = folder.replaceFirst("^(\\w+):.+$", "$1").toLowerCase();
        final String folderPath = folder.replaceFirst("^\\w+:", "");
        if ("file".equals(protocol)) {
            return getSubFolders(Paths.get(folderPath));
        } else if ("classpath".equals(protocol)) {
            final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            final URL url = classLoader.getResource(folderPath);
            if (url == null) {
                throw new IOException("Folder does not exist:" + folder);
            }
            return getSubFolders(Paths.get(url.toURI()));
        } else {
            throw new IOException("Unsupported protocol in path '\"" + folder + "\"'");
        }
    }

    private List<Path> getSubFolders(final Path folder) throws IOException {
        final List<Path> paths = new ArrayList<>();
        Files.walkFileTree(folder, Set.of(), 1, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                if (Files.isDirectory(file)) {
                    paths.add(file);
                }
                return FileVisitResult.CONTINUE;
            }
        });
        return paths;
    }

}
