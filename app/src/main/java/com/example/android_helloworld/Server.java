package com.example.android_helloworld;

import android.content.Context;
import android.util.Log;
import org.json.JSONObject;
import fi.iki.elonen.NanoHTTPD;
import java.io.IOException;
import java.util.Map;

public class Server extends NanoHTTPD {
    private final UserDatabaseHelper dbHelper;

    public Server(Context context, int port) throws IOException {
        super(port);
        this.dbHelper = new UserDatabaseHelper(context);
        Log.i("Server", "Server started on port: " + port);
    }

    @Override
    public Response serve(IHTTPSession session) {
        String uri = session.getUri();
        Method method = session.getMethod();

        Log.d("Server", "Request: " + method + " " + uri);

        if ("/login".equals(uri) && Method.POST.equals(method)) {
            try {
                // Parse body into map
                Map<String, String> body = new java.util.HashMap<>();
                session.parseBody(body);
                String postData = body.get("postData");
                Log.d("Server", "Raw body: " + postData);

                JSONObject json = new JSONObject(postData);
                String username = json.getString("username");
                String password = json.getString("password");

                boolean valid = dbHelper.validateUser(username, password);
                JSONObject responseJson = new JSONObject();

                Log.d("Server", "Username: " + username + ", Password: " + password);

                // Check if it is in the database
                if (valid) {
                    responseJson.put("success", true);
                    responseJson.put("message", "Login successful!");
                    return newFixedLengthResponse(Response.Status.OK, "application/json", responseJson.toString());
                } else {
                    responseJson.put("success", false);
                    responseJson.put("message", "Invalid credentials");
                    return newFixedLengthResponse(Response.Status.UNAUTHORIZED, "application/json", responseJson.toString());
                }
            } catch (Exception e) {
                Log.e("Server", "Error handling /login", e);
                return newFixedLengthResponse(Response.Status.INTERNAL_ERROR, "application/json",
                        "{ \"success\": false, \"message\": \"Server error\" }");
            }
        }

        // Default route (like a health check)
        return newFixedLengthResponse(Response.Status.OK, "text/plain", "Android NanoHTTPD server running!");
    }
}
