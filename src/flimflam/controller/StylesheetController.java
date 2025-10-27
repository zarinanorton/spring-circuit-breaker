package controller;

public class StylesheetController extends Controller {
    @Override
    String getResourcePath() {
        return "resources/css/";
    }
    @Override
    String getContentType() {
        return "text/css";
    }
}
