
import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class HelloWorld {
    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.create(
            new InetSocketAddress(8080), 0
        );

        server.createContext("/", exchange -> {
            byte[] response = "Hello World - Version 1.0"
                .getBytes(StandardCharsets.UTF_8);

            exchange.sendResponseHeaders(200, response.length);

            try (var output = exchange.getResponseBody()) {
                output.write(response);
            }
        });

        server.start();
        System.out.println("Java server running on port 8080");
    }
}

