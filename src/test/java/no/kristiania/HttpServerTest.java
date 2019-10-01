package no.kristiania;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class HttpServerTest {

    private HttpServer server;

    @BeforeEach
    void setUp() throws IOException {
        server = new HttpServer(0);
        server.startServer();
    }

    @Test
    void shouldGet200StatusCode() throws IOException{
        HttpClient client = new HttpClient("localhost", server.getPort(),"/echo");
        HttpClientResponse response = client.executeRequest();
        assertEquals(200,response.getStatusCode());
    }

    @Test
    void shouldGetRequestedStatusCode() throws IOException{
        HttpClient client = new HttpClient("localhost", server.getPort(),"/echo?status=401");
        HttpClientResponse response = client.executeRequest();
        assertEquals(401,response.getStatusCode());
    }

    @Test
    void shouldReturnResponseHeader() throws IOException{
        HttpClient httpClient = new HttpClient("localhost", server.getPort(),
                "/echo?status=302&Location=http://example.com");
        HttpClientResponse response = httpClient.executeRequest();
        assertEquals(302,response.getStatusCode());
        assertEquals("http://example.com",response.getHeader("Location"));
    }

}