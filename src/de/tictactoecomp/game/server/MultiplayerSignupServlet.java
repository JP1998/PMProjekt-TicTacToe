package de.tictactoecomp.game.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.tictactoecomp.game.server.abstr.LoggingServlet;

@WebServlet("/multiplayer-signup")
public class MultiplayerSignupServlet extends LoggingServlet {
    
    public MultiplayerSignupServlet() {
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        super.doGet(req, resp);

        if(MultiplayerServlet.currentPlayerPairs != null &&
                MultiplayerServlet.currentPlayerPairs.containsKey(req.getSession().getId())) {
            req.getRequestDispatcher("/multiplayer")
                .forward(req, resp);
        } else {
            req.getRequestDispatcher("/WEB-INF/multiplayer-signup.html")
                .forward(req, resp);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);

        log("User with sessionId '{0}' has requested /multiplayer-signup via POST request.", req.getSession().getId());
        resp.setStatus(405);
    }
}
