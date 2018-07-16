package de.tictactoecomp.game.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;

import de.tictactoecomp.game.TicTacToeGame;
import de.tictactoecomp.game.playerimpl.AIPlayer;
import de.tictactoecomp.game.playerimpl.MultiPlayerPair;
import de.tictactoecomp.game.playerimpl.SinglePlayer;
import de.tictactoecomp.game.server.abstr.LoggingServlet;
import de.tictactoecomp.game.utils.StringProcessing;

@WebServlet("/multiplayer")
public class MultiplayerServlet extends LoggingServlet {
    
    private static Map<String, MultiPlayerPair> currentPlayerPairs;
    private static final Pattern namingPattern = Pattern.compile("^[a-zA-ZäÄöÖüÜ][a-zA-Z\\-äÄöÖüÜ]{0,19}$");
    
    public MultiplayerServlet() {
        super();
        currentPlayerPairs = new HashMap<>();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        String pairId = session.getId();
        
        if(currentPlayerPairs.containsKey(pairId) == false) {
            String name1 = request.getParameter("player1");
            if(name1 == null || !namingPattern.matcher(name1).matches()) {
                response.setStatus(400);
                return;
            }
            
            String name2 = request.getParameter("player2");
            if(name2 == null || !namingPattern.matcher(name2).matches()) {
                response.setStatus(400);
                return;
            }
            
            MultiPlayerPair newPair = new MultiPlayerPair(
                    pairId, name1, name2);
            
            currentPlayerPairs.put(pairId, newPair);
        }
        
        MultiPlayerPair pair = currentPlayerPairs.get(pairId);
        
        request.getRequestDispatcher(StringProcessing.format(
                "/WEB-INF/multiplayer.jsp?player1={0}&player2={1}",
                pair.getPlayer1Name(),
                pair.getPlayer2Name()
        )).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        log("User with sessionId '{0}' has requested /multiplayer via POST request.", request.getSession().getId());
        response.setStatus(405);
    }
    

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        // if a session of a player expires we'll simply take his game out of the pool.
        if(currentPlayerPairs.containsKey(se.getSession().getId())) {
            log("Game of user pair with id '{0}' has been deleted since their session has expired.");
            currentPlayerPairs.remove(se.getSession().getId());
        }
    }
    
    @WebServlet("/multiplayer/taketurn")
    public static class TakeTurnServlet extends LoggingServlet {
        
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            // GET requests are disabled for taking turns, as they are only posts to the server
            // on which field the user is trying to take.
            log("User with sessionId '{0}' has requested /multiplayer/taketurn via GET request.", req.getSession().getId());
            resp.setStatus(405);
        }
         
        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            if(currentPlayerPairs == null) {
                // the SinglePlayerServlet has not been created yet there cannot be a user registered already
                log("User with sessionId '{0}' has requested /multiplayer/taketurn although /multiplayer is not yet setup.", req.getSession().getId());
                resp.setStatus(401);
                return;
            }
            
             HttpSession session = req.getSession();
             String playerId = session.getId();

             if(!currentPlayerPairs.containsKey(playerId)) {
                 // Since the player is not registered for a game he is not authorized to take a turn
                 log("User with sessionId '{0}' has requested /multiplayer/taketurn although he is not associated with a game yet.", req.getSession().getId());
                 resp.setStatus(401);
                 return;
             }
             
             int field;
             try {
                 field = Integer.parseInt(req.getParameter("field"));
             } catch (NumberFormatException e) {
                 // since there was no / an malformed parameter called field
                 // we'll simply reject the request
                 log("User with sessionId '{0}' has requested /singleplayer/taketurn without or with malformed parameter 'field'.", req.getSession().getId());
                 resp.setStatus(400);
                 return;
             }
             
             currentPlayerPairs.get(playerId).makeMove(field);
        }
    }
    
    @WebServlet("/multiplayer/update")
    public static class UpdateServlet extends LoggingServlet {
        
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            if(currentPlayerPairs == null) {
                // the SinglePlayerServlet has not been created yet there cannot be a user registered already
                log("User with sessionId '{0}' has requested /multiplayer/update although /multiplayer is not yet setup.", req.getSession().getId());
                resp.setStatus(401);
                return;
            }
            
            HttpSession session = req.getSession();
            String playerId = session.getId();

            if(!currentPlayerPairs.containsKey(playerId)) {
                // Since the player is not registered for a game he is not authorized to receive an update
                log("User with sessionId '{0}' has requested /multiplayer/update although he is not associated with a game yet.", req.getSession().getId());
                resp.setStatus(401);
                return;
            }

            resp.getWriter().append(currentPlayerPairs.get(playerId).getStatusJSON());
        }
         
        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            // POST requests are disabled for updates, as they are requests for data from the server
            log("User with sessionId '{0}' has requested /multiplayer/update via POST request.", req.getSession().getId());
            resp.setStatus(405);
        }
    }
    
    @WebServlet("/multiplayer/reset")
    public static class ResetServlet extends LoggingServlet {
        
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            // GET requests are disabled for updates, as they are requests for data from the server
            log("User with sessionId '{0}' has requested /multiplayer/reset via GET request.", req.getSession().getId());
            resp.setStatus(405);
        }
         
        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            if(currentPlayerPairs == null) {
                // the SinglePlayerServlet has not been created yet there cannot be a user registered already
                log("User with sessionId '{0}' has requested /multiplayer/reset although /multiplayer is not yet setup.", req.getSession().getId());
                resp.setStatus(401);
                return;
            }
            
            HttpSession session = req.getSession();
            String pairId = session.getId();

            if(!currentPlayerPairs.containsKey(pairId)) {
                // Since the player is not registered for a game he is not authorized to receive an update
                log("User with sessionId '{0}' has requested /multiplayer/reset although he is not associated with a game yet.", req.getSession().getId());
                resp.setStatus(401);
                return;
            }
            
            MultiPlayerPair oldPair = currentPlayerPairs.get(pairId);
            
            MultiPlayerPair newPair = new MultiPlayerPair(
                    pairId, oldPair.getPlayer1Name(), oldPair.getPlayer2Name());
            
            currentPlayerPairs.put(pairId, newPair);

            
            log("Multi player game has been reset for user with id '{0}'.", pairId);
        }
    }
    
    @WebServlet("/multiplayer/delete")
    public static class DeleteServlet extends LoggingServlet {
        
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            if(currentPlayerPairs == null) {
                // the SinglePlayerServlet has not been created yet there cannot be a user registered already
                log("User with sessionId '{0}' has requested /multiplayer/delete although /multiplayer is not yet setup.", req.getSession().getId());
                resp.setStatus(401);
                return;
            }
            
            HttpSession session = req.getSession();
            String pairId = session.getId();

            if(!currentPlayerPairs.containsKey(pairId)) {
                // Since the player is not registered for a game he is not authorized to receive an update
                log("User with sessionId '{0}' has requested /multiplayer/delete although he is not associated with a game yet.", req.getSession().getId());
                resp.setStatus(401);
                return;
            }
            
            currentPlayerPairs.remove(pairId);
            
            resp.sendRedirect("/PMProjekt-TicTacToe/index");
            
            log("Multi player game has been deleted for user with id '{0}'.", pairId);
        }
         
        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            // GET requests are disabled for updates, as they are requests for data from the server
            log("User with sessionId '{0}' has requested /multiplayer/delete via POST request.", req.getSession().getId());
            resp.setStatus(405);
        }
    }

}
