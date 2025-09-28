package com.example.android_helloworld;

import android.util.Log;
import fi.iki.elonen.NanoHTTPD;

import java.io.IOException;

public class helloServer extends NanoHTTPD {

    public helloServer(int port) throws IOException {
        super(port);
        start(SOCKET_READ_TIMEOUT, false);
        Log.i("HelloServer", "Server started on port: " + port);
    }

    @Override
    public Response serve(IHTTPSession session) {
        String msg = "<html><body><h1>Hello World</h1></body></html>";
        return newFixedLengthResponse(Response.Status.OK, "text/html", msg);
    }
}
