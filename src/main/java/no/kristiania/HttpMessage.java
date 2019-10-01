package no.kristiania;

import java.io.IOException;
import java.io.InputStream;

public class HttpMessage {
    protected String readBytes(InputStream inputStream, int contentLength)throws IOException {
        StringBuilder body = new StringBuilder();
        for (int i = 0; i < contentLength ; i++) {
            body.append((char)inputStream.read());
        }

        return body.toString();
    }
}
