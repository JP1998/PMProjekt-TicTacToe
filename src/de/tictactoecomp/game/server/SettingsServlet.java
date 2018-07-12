package de.tictactoecomp.game.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.tictactoecomp.game.server.abstr.LoggingServlet;

@WebServlet("/settings")
public class SettingsServlet extends LoggingServlet {

    public SettingsServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/settings.html")
            .forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        log("User with sessionId '{0}' has requested /settings via POST request.", request.getSession().getId());
        response.setStatus(405);
    }
}
