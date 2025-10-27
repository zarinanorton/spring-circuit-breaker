package flimflam;

import controller.Controller;
import domain.ResponseEntity;
import registry.ControllerRegistry;
import util.Logger;
import domain.Request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.util.*;

public class HubWebService {
    public static boolean RECEIVING = true;
    Logger logger = Logger.newLogger(HubWebService.class);

    public static void main(String[] args) {
        HubWebService webService = new HubWebService();
        webService.run(8080);
    }

    public void run(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (RECEIVING) {
                try (Socket client = serverSocket.accept()) {
                    handleClient(client);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Request parseHeaders(Socket client) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));

        StringBuilder requestBuilder = new StringBuilder();
        String line;

        while (!(line = br.readLine()).isBlank()) {
            requestBuilder.append(line + "\r\n");
        }

        String request = requestBuilder.toString();
        String[] requestsLines = request.split("\r\n");
        String[] requestLine = requestsLines[0].split(" ");
        String method = requestLine[0];
        String path = requestLine[1];
        String version = requestLine[2];
        String host = requestsLines[1].split(" ")[1];

        List<String> headers = new ArrayList<>();
        for (int h = 2; h < requestsLines.length; h++) {
            String header = requestsLines[h];
            headers.add(header);
        }

        String accessLog = String.format("Client %s, method %s, path %s, version %s, host %s, headers %s",
                client, method, path, version, host, headers);
        logger.info(accessLog);

        return new Request(method, path, version, host);
    }
    private void handleClient(Socket client) throws IOException {
        ControllerRegistry controllerRegistry = new ControllerRegistry();

        logger.debug("Client Request Received: " + client.toString());
        BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));

        Request request = parseHeaders(client);

        Path actualizedPath = Path.of(request.getPath());
        Controller controller = controllerRegistry.getByPath(actualizedPath);
        String fileName = "";
        if (actualizedPath.getFileName() != null) {
            fileName = actualizedPath.getFileName().toString();
        }
        ResponseEntity entity = controller.get(fileName);
        OutputStream clientOutput = client.getOutputStream();
        clientOutput.write("HTTP/1.1 200 OK\r\n".getBytes());
        clientOutput.write(("ContentType: "+entity.getContentType()+"\r\n").getBytes());
        clientOutput.write("\r\n".getBytes());
        clientOutput.write(entity.getContent());
        clientOutput.write("\r\n\r\n".getBytes());
        clientOutput.flush();
        client.close();
    }
}
