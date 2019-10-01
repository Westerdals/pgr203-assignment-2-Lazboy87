package no.kristiania;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

class HttpClientResponse {
    private Socket socket;
    private String statusLine;
    private Map<String ,String> headers = new HashMap<>();

    public HttpClientResponse(Socket socket) {
        this.socket = socket;
    }

    public void invoke() throws IOException {
        InputStream input = socket.getInputStream();
        int c;


        this.statusLine = readLine(socket);
        System.out.println(this.statusLine);

        String headerLine;
        while (!(headerLine = readLine(socket)).isEmpty()) {
            System.out.println(getClass().getSimpleName() + ": " + headerLine);
            int colonPos = headerLine.indexOf(':');
            this.headers.put(headerLine.substring(0, colonPos), headerLine.substring(colonPos+1).trim());

        }
    }

    private static String readLine(Socket socket) throws IOException{
        int c;
        StringBuilder line = new StringBuilder();

        while ((c = socket.getInputStream().read()) != -1) {
            line.append((char)c);

        }
        return line.toString();
    }

    public int getStatusCode() {
        return Integer.parseInt(statusLine.split(" ")[1]);
    }

    public String getHeader(String headerName){
        return headers.get(headerName);
    }

}



