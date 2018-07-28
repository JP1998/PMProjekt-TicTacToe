package de.tictactoecomp.game.server.abstr;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import de.tictactoecomp.game.utils.StringProcessing;

/**
 * Diese Klase bietet ein Grundgerüst, das das Loggen von Events und dem
 * Verbieten des Caching der Webseite, in den Subklassen
 * (also den eigentlichen Servlets der Webseite) erleichtert.
 */
public class LoggingServlet extends HttpServlet implements HttpSessionListener {

    /**
     * Diese Methode schreibt die gegebene Nachricht in die Konsole.
     * Dabei kann die Nachricht Wildcards enthalten, die mit den gegebenen
     * Tokens befüllt werden können.
     * 
     * @param templ  Das Format, in dem die Token eingesetzt werden sollen,
     *               und in die Konsole geschrieben werden soll
     * @param tokens Die Token, die in das gegebene Format eingefügt werden sollen
     * @see StringProcessing#format(String, Object...)
     */
    protected void log(String templ, Object... tokens) {
        System.out.println(StringProcessing.format(templ, tokens));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // verbiete das Caching der Website, um bspw. Weiterleitungen unter bestimmten
        // Konditionen zu ermöglichen
        resp.setHeader("Cache-Control","no-store");     //HTTP 1.1
        resp.setHeader("Pragma","no-cache");            //HTTP 1.0
        resp.setDateHeader ("Expires", 0);              //prevents caching at the proxy server
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // verbiete das Caching der Website, um bspw. Weiterleitungen unter bestimmten
        // Konditionen zu ermöglichen
        resp.setHeader("Cache-Control","no-store");     //HTTP 1.1
        resp.setHeader("Pragma","no-cache");            //HTTP 1.0
        resp.setDateHeader ("Expires", 0);              //prevents caching at the proxy server
    }
    
}
