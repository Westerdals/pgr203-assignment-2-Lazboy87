package no.kristiania;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static no.kristiania.HttpClient.readLine;


public class HttpClientResponse {
    private String statusLine;
    private Map<String, String> headers = new HashMap<>();

    public HttpClientResponse(InputStream inputStream) throws IOException{
        statusLine = readLine(inputStream);
        System.out.println(statusLine);
        String headerLine;
        while (!(headerLine = readLine(inputStream)).isBlank()){
            int colonPos = headerLine.indexOf(':');
            String headerName = headerLine.substring(0,colonPos).trim();
            String headerValue = headerLine.substring(colonPos+1).trim();
            System.out.println("HEADER: " + headerName + "->" + headerValue);
            headers.put(headerName.toLowerCase(),headerValue);
        }
    }





    public int getStatusCode(){
        return Integer.parseInt(statusLine.split(" ")[1]);

    }

    public String getHeader(String headerName) {
        return headers.get(headerName.toLowerCase());
    }
}
