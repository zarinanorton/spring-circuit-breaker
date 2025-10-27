package flimflam.controller;

import flimflam.HubWebService;
import flimflam.domain.ResponseEntity;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public abstract class Controller {

    abstract String getResourcePath();

    abstract String getContentType();

    public ResponseEntity get(String fileName) {
        Path path = Path.of(getResourcePath(), fileName);
        byte[] content = new byte[0];
        try (InputStream in = HubWebService.class.getResourceAsStream(path.toString())) {
            content = new BufferedInputStream(in).readAllBytes();
        } catch (IOException x) {
            System.err.println(x);
        }
        return ResponseEntity.ok(getContentType(), content);
    }
}
