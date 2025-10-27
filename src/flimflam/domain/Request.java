package flimflam.domain;

public class Request {
    String method;
    String path;
    String version;
    String host;

    public Request(String method, String path, String version, String host) {
        this.method = method;
        this.path = path;
        this.version = version;
        this.host = host;
    }

    public String getPath() {
        return this.path;
    }
}
