package flimflam.registry;

import flimflam.controller.Controller;
import flimflam.controller.IndexController;
import flimflam.controller.JavascriptController;
import flimflam.controller.StylesheetController;

import java.nio.file.Path;

public class ControllerRegistry {

    public Controller getByPath(Path path) {
        int nameCount = path.getNameCount();
        if (nameCount == 0) {
            return new IndexController();
        }
        String prefix = path.getName(0).toString();
        switch (prefix) {
            case ("css"): return new StylesheetController();
            case ("js"): return new JavascriptController();
            default: return new IndexController();
        }
    }
}
