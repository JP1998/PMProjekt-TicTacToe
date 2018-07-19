package de.tictactoecomp.game.server.abstr;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import de.tictactoecomp.game.utils.StringProcessing;

public class LoggingServlet extends HttpServlet implements HttpSessionListener {

    protected void log(String templ, Object... tokens) {
        System.out.println(StringProcessing.format(templ, tokens));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {        
        resp.setHeader("Cache-Control","no-store"); //HTTP 1.1
        resp.setHeader("Pragma","no-cache"); //HTTP 1.0
        resp.setDateHeader ("Expires", 0); //prevents caching at the proxy server
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Cache-Control","no-store"); //HTTP 1.1
        resp.setHeader("Pragma","no-cache"); //HTTP 1.0
        resp.setDateHeader ("Expires", 0); //prevents caching at the proxy server
    }
    
}
