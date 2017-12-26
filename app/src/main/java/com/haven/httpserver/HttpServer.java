package com.haven.httpserver;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;

/**
 * Created by lsq on 12/26/2017.12:16 PM
 *
 * @author lsq
 * @describe 添加描述
 */

public class HttpServer extends NanoHTTPD {
    private static final String TAG = HttpServer.class.getSimpleName();


    public HttpServer(int port) {
        super(port);
    }

    /**
     * 注意：POST方式获取body参数需要先调用{@link fi.iki.elonen.NanoHTTPD.IHTTPSession#parseBody(Map)}方法，
     * 然后再调用{@link IHTTPSession#getParms()}才能获取到
     *
     * @param session
     * @return
     */
    @Override
    public Response serve(IHTTPSession session) {
        Log.i(TAG, "receive a serve: " + session.getUri());
        String url = session.getUri();
        Map<String, String> parms = new HashMap<>(4);
        switch (url) {
            case "/login":
                if (session.getMethod() == Method.POST) {
                    try {
                        session.parseBody(new HashMap<String, String>(2));
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ResponseException e) {
                        e.printStackTrace();
                    }
                } else if (session.getMethod() == Method.GET) {
                    //do nothing
                }
                parms = session.getParms();
                break;
            case "/init":
                break;
            default:
                break;
        }

        return newFixedLengthResponse(NanoHTTPD.Response.Status.OK, "text/html", "Hello," + url
                + " use a " + session.getMethod().name() + " method,params is:" + printParmas(parms).toString());
    }

    @NonNull
    private StringBuilder printParmas(Map<String, String> parms) {
        Iterator<Map.Entry<String, String>> iterator = parms.entrySet().iterator();
        StringBuilder sb = new StringBuilder(20);
        Map.Entry<String, String> entry;
        while (iterator.hasNext()) {
            entry = iterator.next();
            sb.append("key:").append(entry.getKey())
                    .append(" value:").append(entry.getValue())
                    .append("\t");
        }
        return sb;
    }

}
