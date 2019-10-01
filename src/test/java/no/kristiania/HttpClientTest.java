package no.kristiania;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class HttpClientTest {


        @Test
    void shouldExecuteHttprequest() throws IOException {
        HttpClient client=new HttpClient("urlecho.appspot.com", 80, "/echo");
        assertEquals(200, client.execute().getStatusCode());}

        @Test
    void shouldReadStatusCode() throws IOException {
        HttpClient client = new HttpClient("urlecho.appspot.com", 80, "/echo?status=401");
        assertEquals(401, client.execute().getStatusCode()); }

        @Test
    void shouldReadHeaders() throws IOException {
        HttpClient client = new HttpClient("urlecho.appspot.com", 80, "/echo?content-type=text/plain");
        assertEquals("text/plain; charset=utf-8", client.execute().getHeader("Content-type")); }
        @Test
    void shouldReadContentLength() throws IOException {
        HttpClient client = new HttpClient("urlecho.appspot.com", 80, "/echo?body=Hello+world!");
        assertEquals(12, client.execute().getContentLenght()); }

}