package no.kristiania;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;


public class HttpServer {


    private static int port;
    private ServerSocket serversocket;


    public HttpServer(int port) throws IOException {
        this.port = port;
        this.serversocket = new ServerSocket(port);
    }


    public static void main(String[] args) throws IOException {
        new HttpServer(8080).startServer();

    }


    public void startServer() throws IOException {

        new Thread(() -> run()).start();

    }

    private void run() {
        try {
            Socket socket = serversocket.accept();

            StringBuilder line = new StringBuilder();
            String firstLine = null;
            int c;
            while ((c = socket.getInputStream().read()) != -1) {
                if (c=='\r'){
                    c = socket.getInputStream().read();
                    if(firstLine == null){
                        firstLine = line.toString();
                    }

                    System.out.println(line);
                    if (line.toString().isBlank()){
                        break;
                    }
                    line = new StringBuilder();
                }
                line.append((char)c);

            }


            String requestTarget = firstLine.split(" ")[1];
            Map<String, String> queryParameters = parseQueryParameters(requestTarget);



           String status = queryParameters.getOrDefault("status","200");
           String location = queryParameters.getOrDefault("Location",null);

            socket.getOutputStream().write(("HTTP/1.1 " + status + " OK\r\n" +
                    "Content-type: text/plain\r\n"+
                    "Content-length: 12\r\n"+
                    "Connection: close\r\n"+
                    (location != null ? "Location: " + location + "r\n" : "")+
                    "\r\n"+
                    "Hello World!").getBytes());
            socket.getOutputStream().flush();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Map<String, String> parseQueryParameters(String requestTarget) {
        Map<String, String> queryParameters = new HashMap<>();
        int questionPos = requestTarget.indexOf('?');
        if(questionPos > 0){
            String query = requestTarget.substring(questionPos+1);
           for(String parameter : query.split("&")) {
               int equalsPos = parameter.indexOf('=');
               String parameterName = parameter.substring(0,equalsPos);
               String parameterValue = parameter.substring(equalsPos+1);
               queryParameters.put(parameterName,parameterValue);

           }
        }
        return queryParameters;
    }



    public int getPort(){
        return serversocket.getLocalPort();
    }

}