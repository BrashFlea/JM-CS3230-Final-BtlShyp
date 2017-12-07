package BtlShypUserInterface;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import lombok.extern.slf4j.Slf4j;
import main.btlshyp.message.AttackResponseMessage;
import main.btlshyp.model.Coordinate;
import main.btlshyp.model.Ship;
import main.btlshyp.view.View;
import main.btlshyp.view.event.AttackEvent;
import main.btlshyp.view.event.AttackListener;
import main.btlshyp.view.event.ChatEvent;
import main.btlshyp.view.event.ChatListener;
import main.btlshyp.view.event.SetShipEvent;
import main.btlshyp.view.event.SetShipListener;
import net.miginfocom.swing.MigLayout;

@Slf4j
public class UserInterface extends View {

  /**
   * Graphical User Interface for the game BtlShyp
   * Author: Jonathan Mirabile
   * Date: 12/7/2017
   */
  private static final long serialVersionUID = 1L;
  private final ImageIcon BTLSHYP_ICON = new ImageIcon("resources/icons8-battleship-96.png");
  private final Font CAMBRIA_BIG_BOLD = new Font("Cambria", Font.BOLD, 16);
  private final Color playerBackground = new Color(73, 103, 134);
  private final Color playerShipHit = new Color(128, 24, 21);
  private final Color playerShipPlaced = new Color(57, 61, 65);
  private final Color playerButtonTextColor = Color.WHITE;
  private final Color opponentBackground = Color.LIGHT_GRAY;
  private final Color opponentShipHit = new Color(230, 18, 18);
  private final Color opponentShipMiss = Color.WHITE;
  private final Color opponentButtonTextColor = Color.BLACK;

  private JFrame shipGridFrame;
  private JPanel gridBoard;
  private JPanel textBoard;
  private JPanel gameBoard;
  private JPanel chatArea;
  private JTextArea chatInput;
  private JTextArea chatOutput;
  private JScrollPane chatOutputScrollbar;
  private JLabel yourBoard;
  private JLabel title;
  private JLabel opponentBoard;
  
  private ArrayList<Coordinate> shipCoordinatesMaster = new ArrayList<Coordinate>();
  private Ship shipToPlace = null;
  private Coordinate attackCoordinate = new Coordinate(-1,-1);
  private BtlButton[][] playerShipGridArray = new BtlButton[5][5];
  private BtlButton[][] opponentShipGridArray = new BtlButton[5][5];

  public UserInterface() {
    initUI();
  }
  
  private void initUI() {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception ex) {
      ex.printStackTrace();
    }
   
    shipGridFrame = new JFrame("Jonathan Mirabile - BtlShyp");
    shipGridFrame.setIconImage(BTLSHYP_ICON.getImage());
    gridBoard = new JPanel(new MigLayout("debug", "[|grow|]"));
    textBoard = new JPanel(new MigLayout("debug, fillx"));
    gameBoard = new JPanel(new MigLayout());
    chatArea = new JPanel(new MigLayout("debug, fill, height 600, width 400"));
    chatOutputScrollbar = new JScrollPane(chatOutput(), JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    
    chatArea.add(chatOutputScrollbar, "wrap, grow, width 350, height 450");
    chatArea.add(chatInput(), "wrap, grow, width 350, height 115");
    chatArea.add(sendButton(), "grow, width 350, height 35");
    
    gridBoard.add(playerShipGridArea());
    gridBoard.add(chatArea, "grow");
    gridBoard.add(opponentShipGridArea());
    
    textBoard.add(yourBoard(), "grow, width 630, height 25");
    textBoard.add(title(), "grow, width 400, height 25");
    textBoard.add(opponentBoard(), "grow, width 630, height 25");
    
    gameBoard.add(gridBoard, "wrap");
    gameBoard.add(textBoard);
   
    shipGridFrame.add(gameBoard);
    shipGridFrame.pack();
    shipGridFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    shipGridFrame.setVisible(true);
  }
  
  private JPanel playerShipGridArea() {
    JPanel shipGrid = new JPanel(new MigLayout("debug, fill"));
    String BUTTON_NO_WRAP_CONSTRAINTS = "wrap, grow, width 120, height 120";
    String BUTTON_WRAP_CONSTRAINTS = "grow, width 120, height 120";

    String[] colLabels = {"A","B","C","D","E"};
    String buttonLabel = "";

    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++) {
        final int inneri = i;
        final int innerj = j;
        Coordinate currentLocation = new Coordinate(i,j);
        buttonLabel = colLabels[j] + Integer.toString(i+1);
        playerShipGridArray[i][j] = new BtlButton(buttonLabel, currentLocation);
        playerShipGridArray[i][j].setContentAreaFilled(false);
        playerShipGridArray[i][j].setBorder(BorderFactory.createRaisedBevelBorder());
        playerShipGridArray[i][j].setOpaque(true);
        playerShipGridArray[i][j].setBackground(playerBackground);
        playerShipGridArray[i][j].setForeground(playerButtonTextColor);
        
        playerShipGridArray[i][j].addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            setShipCoordinates(playerShipGridArray[inneri][innerj], e);
          }
        });
          
        if (j == 4) {
          shipGrid.add(playerShipGridArray[i][j], BUTTON_NO_WRAP_CONSTRAINTS ); 
        }
        else {
          shipGrid.add(playerShipGridArray[i][j], BUTTON_WRAP_CONSTRAINTS);
        }
      }
    }
    return shipGrid;
  }
  
  private JPanel opponentShipGridArea() {
    JPanel shipGrid = new JPanel(new MigLayout("debug, fill"));
    String BUTTON_NO_WRAP_CONSTRAINTS = "wrap, grow, width 120, height 120";
    String BUTTON_WRAP_CONSTRAINTS = "grow, width 120, height 120";
    
    String[] colLabels = {"A","B","C","D","E"};
    String buttonLabel = "";

    for (int i = 0; i < 5; i++) {
      for(int j = 0; j < 5; j++) {
        final int inneri = i;
        final int innerj = j;
        Coordinate currentLocation = new Coordinate(i,j);
        buttonLabel = colLabels[j] + Integer.toString(i+1);
        opponentShipGridArray[i][j] = new BtlButton(buttonLabel, currentLocation);
        opponentShipGridArray[i][j].setContentAreaFilled(false);
        opponentShipGridArray[i][j].setBorder(BorderFactory.createRaisedBevelBorder());
        opponentShipGridArray[i][j].setOpaque(true);
        opponentShipGridArray[i][j].setBackground(opponentBackground);
        opponentShipGridArray[i][j].setForeground(opponentButtonTextColor);
        
        opponentShipGridArray[i][j].addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            setAttackCoordinates(opponentShipGridArray[inneri][innerj], e);         
          }
        });
        
        if (j == 4) {
          shipGrid.add(opponentShipGridArray[i][j], BUTTON_NO_WRAP_CONSTRAINTS); 
        }
        else {
          shipGrid.add(opponentShipGridArray[i][j], BUTTON_WRAP_CONSTRAINTS);
        }
      }
    }
    return shipGrid;
  }
  
  private JTextArea chatInput() {
    chatInput = new JTextArea();
    chatInput.setLineWrap(true);

    return chatInput;
  }
  
  private JButton sendButton() {
    JButton sendButton = new JButton("Send");
    
    sendButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        sendChat(e);
        chatInput.setText("");
      }
    });
    
    return sendButton;
  }
  
  private JTextArea chatOutput() {
    chatOutput = new JTextArea();
    chatOutput.setLineWrap(true);
    chatOutput.setEditable(false);
    
    return chatOutput;
  }
  
  private JLabel yourBoard() {
    yourBoard = new JLabel("Your Board", SwingConstants.CENTER);
    yourBoard.setFont(CAMBRIA_BIG_BOLD);
    return yourBoard;
  }
  
  private JLabel title() {
    title = new JLabel("Jonathan Mirabile - BtlShyp", SwingConstants.CENTER);
    title.setFont(CAMBRIA_BIG_BOLD);
    return title;
  }
  
  private JLabel opponentBoard() {
    opponentBoard = new JLabel("Opponent Board", SwingConstants.CENTER);
    opponentBoard.setFont(CAMBRIA_BIG_BOLD);
    return opponentBoard;
  }
  
  @Override
  public void registerChatListener(ChatListener listener) {
    this.chatListener = listener;
  }

  @Override
  public void registerSetShipListener(SetShipListener listener) {
    this.setShipListener = listener;
  }

  @Override
  public void registerAttackListener(AttackListener listener) {
    this.attackListener = listener;
  }
  
  public Coordinate getAttackCoordinate() {
    return attackCoordinate;
  }

  public void setAttackCoordinate(Coordinate attackCoordinate) {
    this.attackCoordinate = attackCoordinate;
  }
  
  /**
   * Handles formatting messages before they are displayed
   * Scrolls the chat window to the bottom when placing a new message
   * @param message
   */
  public void display(String message) {
    chatOutput.append(message + "\n");
    scrollChatWindowToBottom();
  }
    
  /**
   * Displays community chat messages that are broadcast to all
   * @param chat
   */
  @Override
  public void displayChat(String user, String chat) {
    if (user != null) {
      display(user + ": " + chat);
    }
    else {
      displayNotification(chat);
    }
  };
  
  /**
   * Displays notifications unique to the user, such as "Please re-place your boats", "Waiting for opponent", etc
   */
  @Override
  public void displayNotification(String text) {
    display(text);
  };
  
  /** 
   * Emits ChatEvent for controller to catch which includes a string message to send out to the server
   */
  @Override
  public void sendChat(ActionEvent e) {
    String chat = chatInput.getText();
    ChatEvent chatEvent = new ChatEvent(this, chat);
    if (chatListener != null) {
      chatListener.chatEventOccurred(chatEvent);
    }
  };
   
  /** 
   * Unlocks the inputs on the game portion of the GUI
   * Prompts user for attack
   */
  @Override
  public void yourTurn() {
    displayNotification("It is now your turn");
    
    // Lock playerShipGridArray
    for(int i = 0; i < playerShipGridArray.length; i++) {
      for (int j = 0; j < playerShipGridArray.length; j++) {
        playerShipGridArray[i][j].setEnabled(true);
      }
    }
    // Lock opponentShipGridArray
    for(int i = 0; i < opponentShipGridArray.length; i++) {
      for (int j = 0; j < opponentShipGridArray.length; j++) {
        opponentShipGridArray[i][j].setEnabled(true);
      }
    }
  };
  
  /**
   * Locks game GUI 
   * Displays wait message
   */
  @Override
  public void notYourTurn() {
    displayNotification("It is now your opponents turn");
    
    // Lock playerShipGridArray
    for(int i = 0; i < playerShipGridArray.length; i++) {
      for (int j = 0; j < playerShipGridArray.length; j++) {
        playerShipGridArray[i][j].setEnabled(false);
      }
    }
    // Lock opponentShipGridArray
    for(int i = 0; i < opponentShipGridArray.length; i++) {
      for (int j = 0; j < opponentShipGridArray.length; j++) {
        opponentShipGridArray[i][j].setEnabled(false);
      }
    }
  };
  
  /**
   * Receives ship from controller for user to place
   * @param ship
   */
  @Override
  public void setShip(Ship ship) {
    display("Please place this " + ship.getShipType() +"\n" + 
        "It is " + ship.getShipSize() +  " blocks long");
    this.shipToPlace = ship;
    log.info("setShip: ship looks like {}", ship);
    shipCoordinatesMaster.clear();
  };
   
  /**
   * Extract the coordinates from a BtlButton ship placement event
   * @param BtlButton
   */
  public void setShipCoordinates(BtlButton shipPlacedButton, ActionEvent e) {
    Coordinate attemptedPlacement = shipPlacedButton.getCoordinates();
    shipCoordinatesMaster.add(attemptedPlacement);
    
    if(this.shipToPlace != null) {
      if (shipCoordinatesMaster.size() == this.shipToPlace.getShipSize()) {
        attemptSetShip(e);
      } 
    }   
  };
  
  /**
   * Collects coordinates into a ship and emits a setShipEvent
   */
  @Override
  public void attemptSetShip(ActionEvent e) {
    Ship setShipEventShip = new Ship(shipToPlace.getShipType());
    ArrayList<Coordinate> shipCoordinates = new ArrayList<Coordinate>(shipCoordinatesMaster);
    
    setShipEventShip.setShipCoordinates(shipCoordinates);
    log.info("setShipEventShip looks like: {}", setShipEventShip);
    log.info("setShipEventShip size = {}, size of shipCoordinates =  {}", setShipEventShip.getShipSize(), shipCoordinates.size());
   
    if (setShipEventShip != null) {
      SetShipEvent sse = new SetShipEvent(e, setShipEventShip);
      if (setShipListener != null) {
        log.info("Attempting to set ship: {}", setShipEventShip);
        setShipListener.setShipEventOccurred(sse);
      }   
    }
    setShipEventShip = null;
  };
  
  /**
   * Displays a properly placed ship on players board
   * @param ship
   */
  @Override
  public void displayShip(Ship ship) {
    ArrayList<Coordinate> shipCoordinates = ship.getShipCoordinates();
    for(int i = 0; i < shipCoordinates.size(); i++) {
      Coordinate currentCoordinate = shipCoordinates.get(i);
      playerShipGridArray[currentCoordinate.x][currentCoordinate.y].setBackground(playerShipPlaced);
    } 
  };
  
  /**
   * Extract the coordinates from a BtlButton attack event
   * @param BtlButton
   */
  public void setAttackCoordinates(BtlButton attackAttemptButton, ActionEvent e) {
    setAttackCoordinate(attackAttemptButton.getCoordinates());
    log.info("Setting attack coordinates to: {}", getAttackCoordinate());
    sendAttack(e);
  };
  
  /**
   * Sends controller the coordinate to attack
   * @param AttackCoordinate
   */
  @Override
  public void sendAttack(ActionEvent e) {
    AttackEvent ae = new AttackEvent(e, getAttackCoordinate());
    log.info("Sending attack on: {} ", getAttackCoordinate());
    if (attackListener != null) {
      attackListener.attackEventOccurred(ae);
    }
  };
  
  /**
   * Displays the results of your attack attempt on your opponent. Analogous to putting a pin in the 
   * vertical portion of the traditional board game.
   * @param hitOrMiss
   * @param coordinate
   */
  public void displayAttack(AttackResponseMessage message) {
    Coordinate messageCoord = message.getCoordinate();
    int coordx = messageCoord.x;
    int coordy = messageCoord.y;
    
    displayNotification("Attacked opponent on " + convertCoordinate(getAttackCoordinate()) + "\n"
        + "It was a " + message.getHitOrMiss());
    if(message.getHitOrMiss().toString() == "HIT") {
      opponentShipGridArray[coordx][coordy].setBackground(opponentShipHit);
      opponentShipGridArray[coordx][coordy].setText(
          opponentShipGridArray[coordx][coordy].getText() + "_HIT");
    }
    if(message.getHitOrMiss().toString() == "MISS") {
      opponentShipGridArray[coordx][coordy].setBackground(opponentShipMiss);
      opponentShipGridArray[coordx][coordy].setText(
          opponentShipGridArray[coordx][coordy].getText() + "_MISS");
    }
    if(message.getShipSunk().toString() != "NONE") {
      displayNotification("You sunk your opponent's " + message.getShipSunk());
    }
  };
  
  /**
   * Displays the results of an opponent's attack on you. Analogous to putting a pin in a boat
   * or the sea on the horizontal part of the traditional board game.
   * @param hitOrMiss
   * @param coordinate
   */
  public void displayOpponentAttack(AttackResponseMessage message) {
    Coordinate messageCoord = message.getCoordinate();
    int coordx = messageCoord.x;
    int coordy = messageCoord.y;
    
    displayNotification("Opponent attacked you on " +  convertCoordinate(message.getCoordinate()) + "\n"
        + "It was a " + message.getHitOrMiss());
    if(message.getHitOrMiss().toString() == "HIT") {
      playerShipGridArray[coordx][coordy].setBackground(playerShipHit);
      playerShipGridArray[coordx][coordy].setText(
          playerShipGridArray[coordx][coordy].getText() + "_HIT");
    }
    if(message.getHitOrMiss().toString() == "MISS") {
      // Do nothing. This looks the most visually appealing on the GUI 
    }
    if(message.getShipSunk().toString() != "NONE") {
      displayNotification("Your opponent sunk your " + message.getShipSunk());
    }
  };
  
  /**
   * Resets gui to gameless state
   */
  @Override
  public void resetGame() {
    String[] colLabels = {"A","B","C","D","E"};
    String buttonLabel = "";
    
    // Reset playerShipGridArray
    for(int i = 0; i < playerShipGridArray.length; i++) {
      for (int j = 0; j < playerShipGridArray.length; j++) {
        buttonLabel = colLabels[j] + Integer.toString(i+1);
        playerShipGridArray[i][j].setText(buttonLabel);
        playerShipGridArray[i][j].setBackground(playerBackground);
        playerShipGridArray[i][j].setEnabled(true);
      }
    }
    // Reset opponentShipGridArray
    for(int i = 0; i < opponentShipGridArray.length; i++) {
      for (int j = 0; j < opponentShipGridArray.length; j++) {
        buttonLabel = colLabels[j] + Integer.toString(i+1);
        opponentShipGridArray[i][j].setText(buttonLabel);
        opponentShipGridArray[i][j].setBackground(opponentBackground);
        opponentShipGridArray[i][j].setEnabled(true);
      }
    }
  };
  
  /**
   * Convert Coordinates into standard BtlShyp style
   * Ex: (0,0) becomes A1
   */
  public String convertCoordinate(Coordinate coord) {
    int x = coord.x;
    int y = coord.y;
    String[] colLabels = {"A","B","C","D","E"};
    
    String convertedCoordinate = colLabels[y] + (x + 1);
    return convertedCoordinate;
  }
  
  /**
   * Scrolls the messages text area down to the bottom
   */
  public void scrollChatWindowToBottom() {
    Point bottomScrollPoint = new Point(0, chatOutput.getDocument().getLength());
    chatOutputScrollbar.getViewport().setViewPosition(bottomScrollPoint);
  }
  
} // end class

