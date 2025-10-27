package controller;

import domain.ResponseEntity;

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
