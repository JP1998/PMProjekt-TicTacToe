package de.tictactoecomp.game;

/**
 * Diese Klasse dient als Abstrahierung eines Spielers, der an einem
 * Tic Tac Toe-Spiel teilnehmen kann. Diese Abstrahierung wird vorgenommen,
 * um auf einfache Art und Weise eine KI implementieren zu können.
 * 
 * @author Jonny
 */
public abstract class Player {
	
    public static final String JSON_TEMPLATE = "{{{0}    \"turn\" : {1},{0}    \"fields\" : [ {2} ],{0}    \"message\" : \"{3}\",{0}    \"gameEnded\" : {4},{0}    \"playerwithturn\" : \"{5}\"{0}}}";
    
    /**
     * Die ID des Spielers
     */
    protected String playerId;
    /**
     * Der Name des Spielers
     */
    protected String name;
    /**
     * Eine Nachricht vom Server für den Client.
     */
    private String message;
    /**
     * Das Spiel, in dem sich der Spieler momentan befindet
     */
    protected TicTacToeGame currentGame;
    
    public Player(String id, String name) {
        this.playerId = id;
        this.name = name;
        this.message = "";
        this.currentGame = null;
    }
    
    /**
     * Diese Methode gibt die ID des Spielers zurück.
     * 
     * @return die ID des Spielers
     */
    public String getPlayerId() {
        return playerId;
    }
    
    /**
     * Diese Methode gibt den Namen des Spielers zurück.
     * 
     * @return den Namen des Spielers
     */
    public String getName() {
        return name;
    }
    
    /**
     * Diese Methode informiert den Spieler darüber, dass ihm das gegebene
     * Spiel zugewiesen wurde.
     * 
     * @param game das Spiel, das dem Spieler zugewiesen wurde
     */
    /* package-protected */ void notifyOfGameAttachment(TicTacToeGame game) {
        this.currentGame = game;
    }
    
    /**
     * Diese Methode setzt eine Nachricht, die für den mit diesem Spieler
     * asoziierten Client bestimmt ist.
     * 
     * @param message die Nachricht für den Client
     */
    public void setMessage(String message) {
        this.message = message;
    }
    
    /**
     * Diese Methode gibt Ihnen eine Nachricht, die für den Client
     * dieses Spielers bestimmt ist.
     * 
     * @return eine Nachricht für den Client dieses Spielers
     */
    public String getMessage() {
        return message;
    }
    
    /**
     * Diese Methode kann genutzt werden, um
     * den Spieler einen Zug machen zu lassen.
     * Dazu kann die Methode {@link TicTacToeGame#receiveMove(long, int)}
     * auf dem derzeitigen Spiel des Spielers aufgerufen werden.
     */
    public abstract void makeMove(int field);
    
    /**
     * Diese Methode erzeugt das JSON, das den Status des Spieles aus der
     * Sicht des Spielers repräsentiert. Dieses kann dann dem Client, der
     * mit diesem Spieler assoziiert ist übermittelt werden.
     * 
     * @return das JSON, das den Status des Spieles enthält
     */
    public abstract String getStatusJSON();
    
    /**
     * Diese Methode ermittelt, ob der Spieler derzeitig in seinem Spiel am Zug ist.
     * 
     * @return ob der Spieler am Zug ist
     */
    protected boolean hasTurn() {
        return playerId.equals(currentGame.getCurrentGameState().getCurrentTurnPlayerId());
    }
    
    /**
     * Diese Methode erstellt den Inhalt des JSON-Arrays, das die einzelnen Züge des
     * Spiels, das diesem Spieler zugeteilt ist, darstellt.
     * 
     * @return die einzelnen Züge des Spiels als Integer, im JSON-validen Format
     */
    protected String createFieldsArray() {
        int[] fields = currentGame.getCurrentGameState().getFields();
        
        StringBuilder result = new StringBuilder();
        
        for(int i = 0; i < fields.length; i++) {
            if(i != 0) {
                result.append(", ");
            }
            result.append(Integer.toString(fields[i]));
        }
        
        return result.toString();
    }
    
}
