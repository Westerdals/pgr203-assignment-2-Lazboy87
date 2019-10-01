package no.kristiania;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;


public class HttpServer {

    private ServerSocket serverSocket;


    public HttpServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public static void main(String[] args) throws IOException {
        new HttpServer(8080).start();

    }

    void start() {

        new Thread(this::run).start();

    }

    private void run() {
        try {

            Socket socket = serverSocket.accept();

            String requestLine = HttpClient.readLine(socket.getInputStream());
            String statusCode = "200";

            String requestTarget = requestLine.split(" ")[1];
            int questionPos = requestTarget.indexOf('?');
            if(questionPos != -1) {
                String querry = requestTarget.substring(questionPos);
                int equalPos = querry.indexOf('=');
                String parameterValue = querry.substring(equalPos+1);
                statusCode = parameterValue;
            }


            socket.getOutputStream().write(("HTTP/1.0 " + statusCode + " OK\r\n" +
                    "Content-length: 12 \r\n" +
                    "\r\n" +
                    "Hello World!").getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getPort(){
        return serverSocket.getLocalPort();
    }
}