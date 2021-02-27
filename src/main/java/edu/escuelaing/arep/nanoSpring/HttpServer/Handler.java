package edu.escuelaing.arep.nanoSpring.HttpServer;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Interface to process the path, request and response
 * @param <T>
 */
public interface Handler<T> {
    public T handle(String path, HttpRequest req, HttpResponse res);
}
