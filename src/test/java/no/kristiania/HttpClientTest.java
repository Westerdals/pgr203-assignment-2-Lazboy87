package no.kristiania;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class HttpClientTest {
    @Test
    void mathShouldWork(){
        assertEquals(4, 2+2);
    }

    @Test
    void shouldReturnStatusCode200()throws IOException {
        HttpClient client = new HttpClient("urlecho.appspot.com", 80, "/echo");
        HttpClientResponse response = client.executeRequest();
        assertEquals(200, response.getStatusCode());

    }
    @Test
    void shouldReturnStatusCode401()throws IOException {
        HttpClient client = new HttpClient("urlecho.appspot.com", 80,"/echo?status=401");
        HttpClientResponse response = client.executeRequest();
        assertEquals(401, response.getStatusCode());

    }




}