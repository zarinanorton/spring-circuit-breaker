package util;

public class Logger {

    final Class<?> introspectiveClass;
    private Logger(Class<?> introspectiveClass) {
        this.introspectiveClass = introspectiveClass;
    }

    public static Logger newLogger(Class<?> introspectiveClass) {
        return new Logger(introspectiveClass);
    }
    public void info(String message) {
        System.out.println(introspectiveClass.getName() + " [INFO] " + message);
    }

    public void debug(String message) {
        System.out.println(introspectiveClass.getName() + " [DEBUG] " + message);
    }

    public void error(String message, Throwable t) {
        System.out.print(message);
        System.out.print(t.getMessage());
        System.out.println(t.getStackTrace());
    }
}
