package no.kristiania;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;


import static org.junit.jupiter.api.Assertions.*;

class HttpServerTest {


    @Test
    void mathShouldWork(){
        assertEquals(4, 2+2);
    }

    @Test
    void shouldGet200StatusCode() throws IOException{
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
    /*
    215
    private HttpServer server;

    @BeforeEach
    void setUp() throws IOException {
        server = new HttpServer(0);
        server.startServer();
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
*/
}