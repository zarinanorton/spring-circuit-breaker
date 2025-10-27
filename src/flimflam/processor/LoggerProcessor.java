package flimflam.processor;

import flimflam.annotations.Logger;
import javax.annotation.processing.*;
import javax.lang.model.element.*;
import java.util.Set;

public class LoggerProcessor {

    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(Logger.class)) {
            if (element.getKind() == ElementKind.CLASS) {
                Logger info = element.getAnnotation(Logger.class);
                String className = element.getSimpleName().toString();
                System.out.println("Found @Logger on class: " + className);
            }
        }
        return true; // No further processing of this annotation
    }
}
