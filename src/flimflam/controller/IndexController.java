package flimflam.controller;

import flimflam.HubWebService;
import flimflam.domain.ResponseEntity;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public class IndexController extends Controller {

    @Override
    String getResourcePath() {
        return "resources/pages/";
    }

    @Override
    String getContentType() {
        return "text/html";
    }

    public ResponseEntity get(String fileName) {
        return super.get("index.html");
    }

}
