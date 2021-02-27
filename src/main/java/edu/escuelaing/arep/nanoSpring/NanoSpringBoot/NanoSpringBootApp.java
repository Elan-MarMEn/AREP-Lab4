package edu.escuelaing.arep.nanoSpring.NanoSpringBoot;

import edu.escuelaing.arep.nanoSpring.HttpServer.Handler;
import edu.escuelaing.arep.nanoSpring.HttpServer.HttpServer;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Benavides(Modify by ALan)
 */
public class NanoSpringBootApp implements Handler<String> {

    private static NanoSpringBootApp instance = new NanoSpringBootApp();
    private boolean componentLoaded = false;
    private Map<String,Method> componentsRoutes = new HashMap();

    private final String BadRequest = "HTTP/1.1 400 Not Found\r\n"
            + "Content-Type: text/html\r\n"
            + "\r\n"
            + "<!DOCTYPE html>\n"
            + "<html>\n"
            + "<head>\n"
            + "<meta charset=\"UTF-8\">\n"
            + "<title>Error 404</title>\n"
            + "</head>\n"
            + "<body>\n"
            + "<h1>Error 404</h1>\n"
            + "<h3>URI not Found</h3>\n"
            + "</body>\n"
            +"<html>\n";

    private final String OkHeader = "HTTP/1.1 200 OK\r\n"
            + "Content-Type: text/html\r\n"
            + "\r\n";

    private NanoSpringBootApp(){
    }

    /**
     * Take the components and uses to map the request Mapping
     * @param args
     */
    public static void run(String[] args){
        if (!instance.componentLoaded){
            try {
                instance.loadComponent(args);
                instance.componentLoaded = true;
                instance.startServer();
            } catch (ClassNotFoundException |IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Return the value from a method request mapping
     * @param stmethod
     * @return String
     */
    public static String  invoke(Method stmethod){
        String temp="";
        try {
             temp=stmethod.invoke(null).toString();
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return temp;
    }

    /**
     * Start the http server and makes a register
     * @throws IOException
     */
    private void startServer() throws IOException {
        HttpServer hserver = new HttpServer();
        hserver.registerHandler(this,"/nspapp");
        hserver.starserver();
    }

    /**
     * Method to handle the predefined result of a requesMapping code
     * @param components
     * @throws ClassNotFoundException
     */
    private void loadComponent(String[] components) throws ClassNotFoundException{
        for (String cp: components) {
            for (Method m: Class.forName(cp).getMethods()){
                if (m.isAnnotationPresent(RequestMapping.class)) {
                    componentsRoutes.put(m.getAnnotation(RequestMapping.class).value(),m);
                }
            }
        }

    }

    @Override
    public String handle(String path, HttpRequest req, HttpResponse res) {
        if (componentsRoutes.containsKey(path)) {

             return OkHeader + invoke(componentsRoutes.get(path));
        }
        return BadRequest;
    }
}
