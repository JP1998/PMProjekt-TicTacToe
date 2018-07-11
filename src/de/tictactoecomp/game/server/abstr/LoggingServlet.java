package de.tictactoecomp.game.server.abstr;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import de.tictactoecomp.game.utils.StringProcessing;

public class LoggingServlet extends HttpServlet implements HttpSessionListener {

    protected void log(String templ, Object... tokens) {
        System.out.println(StringProcessing.format(templ, tokens));
    }

}
