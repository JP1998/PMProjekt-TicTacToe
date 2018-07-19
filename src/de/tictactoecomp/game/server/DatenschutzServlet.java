package de.tictactoecomp.game.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.tictactoecomp.game.server.abstr.LoggingServlet;

@WebServlet("/datenschutz")
public class DatenschutzServlet extends LoggingServlet {

    public DatenschutzServlet() {
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        super.doGet(req, resp);

        req.getRequestDispatcher("/WEB-INF/datenschutz.html")
            .forward(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);

        log("User with sessionId '{0}' has requested /datenschutz via POST request.", req.getSession().getId());
        resp.setStatus(405);
    }
    
}
