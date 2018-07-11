package de.tictactoecomp.game.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;

import de.tictactoecomp.game.*;
import de.tictactoecomp.game.playerimpl.AIPlayer;
import de.tictactoecomp.game.playerimpl.SinglePlayer;
import de.tictactoecomp.game.server.abstr.LoggingServlet;

@WebServlet("/singleplayer")
public class SinglePlayerServlet extends LoggingServlet {
    
    private static Map<String, Player> currentPlayers;

    public SinglePlayerServlet() {
        super();
        currentPlayers = new HashMap<>();
    }
    
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String playerId = session.getId();
	    
        // if the player is not yet registered 
	    if(currentPlayers.containsKey(playerId) == false) {
            SinglePlayer player = new SinglePlayer(
                    playerId,
                    "Player 1"
            );
            
            currentPlayers.put(
                    request.getSession().getId(),
                    player
            );
            
            new TicTacToeGame(player, new AIPlayer());
            
            log("Single player game created for user with id '{0}'.", playerId);
        }
	    
	    request.getRequestDispatcher("/WEB-INF/singleplayer.html")
            .forward(request, response);
	}

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    // POST requests are disabled, since this site is only supposed to be called by the browser
        log("User with sessionId '{0}' has requested /singleplayer via POST request.", request.getSession().getId());
	    response.setStatus(405);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
	    // if a session of a player expires we'll simply take his game out of the pool.
	    if(currentPlayers.containsKey(se.getSession().getId())) {
	        log("Game of user with id '{0}' has been deleted since his session has expired.");
	        currentPlayers.remove(se.getSession().getId());
	    }
	}
	
    @WebServlet("/singleplayer/taketurn")
    public static class TakeTurnServlet extends LoggingServlet {
        
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            // GET requests are disabled for taking turns, as they are only posts to the server
            // on which field the user is trying to take.
            log("User with sessionId '{0}' has requested /singleplayer/taketurn via GET request.", req.getSession().getId());
            resp.setStatus(405);
        }
         
        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            if(currentPlayers == null) {
                // the SinglePlayerServlet has not been created yet there cannot be a user registered already
                log("User with sessionId '{0}' has requested /singleplayer/taketurn although /singleplayer is not yet setup.", req.getSession().getId());
                resp.setStatus(401);
                return;
            }
            
             HttpSession session = req.getSession();
             String playerId = session.getId();

             if(!currentPlayers.containsKey(playerId)) {
                 // Since the player is not registered for a game he is not authorized to take a turn
                 log("User with sessionId '{0}' has requested /singleplayer/taketurn although he is not associated with a game yet.", req.getSession().getId());
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
             
             currentPlayers.get(playerId).makeMove(field);
        }
    }
    
    @WebServlet("/singleplayer/update")
    public static class UpdateServlet extends LoggingServlet {
        
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            if(currentPlayers == null) {
                // the SinglePlayerServlet has not been created yet there cannot be a user registered already
                log("User with sessionId '{0}' has requested /singleplayer/update although /singleplayer is not yet setup.", req.getSession().getId());
                resp.setStatus(401);
                return;
            }
            
            HttpSession session = req.getSession();
            String playerId = session.getId();

            if(!currentPlayers.containsKey(playerId)) {
                // Since the player is not registered for a game he is not authorized to receive an update
                log("User with sessionId '{0}' has requested /singleplayer/update although he is not associated with a game yet.", req.getSession().getId());
                resp.setStatus(401);
                return;
            }
            
            resp.getWriter().append(currentPlayers.get(playerId).getStatusJSON());
        }
         
        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            // POST requests are disabled for updates, as they are requests for data from the server
            log("User with sessionId '{0}' has requested /singleplayer/update via POST request.", req.getSession().getId());
            resp.setStatus(405);
        }
    }
    
    // TODO: Maybe make another domain for restarting the game
}
