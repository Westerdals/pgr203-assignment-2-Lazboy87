package no.kristiania;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


import static org.junit.jupiter.api.Assertions.*;

class HttpServerTest {


    @Test
    void mathShouldWork() {
        assertEquals(4, 2 + 2);
    }

    @Test
    void shouldGet200StatusCode() throws IOException {
        HttpServer server = new HttpServer(0);
        server.start();
        HttpClient client = new HttpClient("localhost", server.getPort(), "/echo");
        assertEquals(200, client.execute().getStatusCode());

    }

    @Test
    void shouldReturnStatusCode401() throws IOException {
        HttpServer server = new HttpServer(0);
        server.start();
        HttpClient client = new HttpClient("localhost", server.getPort(), "/echo?status=401");
        assertEquals(401, client.execute().getStatusCode());
    }

    private HttpServer server;

    @BeforeEach
    void setUp() throws IOException {
        server = new HttpServer(0);
        server.start();
    }


    @Test
    void shouldGetRequestedStatusCode() throws IOException {
        HttpClient client = new HttpClient("localhost", server.getPort(), "/echo?status=401");
        HttpClientResponse response = client.execute();
        assertEquals(401, response.getStatusCode());
    }

    @Test
    void shouldReturnResponseHeader() throws IOException {
        HttpClient client = new HttpClient("localhost", server.getPort(),
                "/echo?status=302&location=http://example.com");
        HttpClientResponse response = client.execute();
        assertEquals(302, response.getStatusCode());
        assertEquals("http://example.com", response.getHeader("location"));
    }

    @Test
    void shouldReadBody() throws IOException {
        HttpClient client = new HttpClient("localhost", server.getPort(), "/echo?body=helloWorld!");
        assertEquals("helloWorld!", client.execute().getBody());
    }


    @Test
    void shouldReturnFileFromDisk() throws IOException {
        String text = "Hello Kristiania";
        Files.writeString(Paths.get("target/myfile.txt"), text);
        server.setFileLocation("target");
        HttpClient client = new HttpClient("localhost", server.getPort(), "/myfile.txt");
        HttpClientResponse response = client.execute();
        assertEquals(text, response.getBody());
    }

}
