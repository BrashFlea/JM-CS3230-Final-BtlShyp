package BtlShypUserInterface;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import lombok.extern.slf4j.Slf4j;
import main.btlshyp.controller.Controller;
import main.btlshyp.message.AttackResponseMessage;
import main.btlshyp.model.Coordinate;
import main.btlshyp.model.Ship;
import main.btlshyp.model.ShipType;
import main.btlshyp.view.View;
import main.btlshyp.view.event.AttackEvent;
import main.btlshyp.view.event.AttackListener;
import main.btlshyp.view.event.ChatEvent;
import main.btlshyp.view.event.ChatListener;
import main.btlshyp.view.event.SetShipEvent;
import main.btlshyp.view.event.SetShipListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.miginfocom.swing.MigLayout;

@Slf4j
public class userInterface extends View {

  private final long serialVersionUID = 1L;
  private final Font CAMBRIA = new Font("Cambria", Font.PLAIN, 16);
  private final Font CAMBRIA_BIGGER = new Font("Cambria", Font.PLAIN, 18);
  private final Font CAMBRIA_BIGGEST = new Font("Cambria", Font.PLAIN, 20);
  private final ImageIcon BTLSHYP_ICON = new ImageIcon("resources/icons8-battleship-96.png");
  
  private JTextArea chatInput;
  private JTextArea chatOutput;
  private JScrollPane chatOutputScrollbar;
  
  private JFrame shipGridFrame;
  private JFrame playAgainFrame;
  private JPanel gridBoard;
  private JPanel chatArea;
  
  private ArrayList<Coordinate> shipCoordinatesMaster = new ArrayList<Coordinate>();
  private Ship shipToPlace = null;
  private Coordinate attackCoordinate = new Coordinate(-1,-1);

  public userInterface() {
    initUI();
  }
  
  private void initUI() {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    
    playAgainFrame = new JFrame("BtlShyp");
    playAgainFrame.getContentPane().add(checkForPlayagain());
    playAgainFrame.setIconImage(BTLSHYP_ICON.getImage());
    playAgainFrame.pack();
    playAgainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    playAgainFrame.setVisible(true);
    
     
    shipGridFrame = new JFrame("Jonathan Mirabile - BtlShyp");
    shipGridFrame.setIconImage(BTLSHYP_ICON.getImage());
    gridBoard = new JPanel(new MigLayout("debug", "[][grow][]"));
    chatArea = new JPanel(new MigLayout("debug, fill, height 600, width 400"));
    chatOutputScrollbar = new JScrollPane(chatOutput(), JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    
    chatArea.add(chatOutputScrollbar, "wrap, grow, width 350, height 450");
    chatArea.add(chatInput(), "wrap, grow, width 350, height 115");
    chatArea.add(sendButton(), "grow, width 350, height 35");
    
    
    gridBoard.add(playerShipGridArea());
    gridBoard.add(chatArea, "grow");
    gridBoard.add(opponentShipGridArea());
    
    
    shipGridFrame.add(gridBoard);
    shipGridFrame.pack();
    shipGridFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    shipGridFrame.setVisible(true);
  }
  
  
  private JPanel playerShipGridArea() {
    JPanel shipGrid = new JPanel(new MigLayout("debug, fill"));
    String BUTTON_NO_WRAP_CONSTRAINTS = "wrap, grow, width 120, height 120";
    String BUTTON__WRAP_CONSTRAINTS = "grow, width 120, height 120";

    String[] colLabels = {"A","B","C","D","E"};
    String buttonLabel = "";

    for (int i = 0; i < 5; i++) {
      for(int j = 0; j < 5; j++) {
        Coordinate currentLocation = new Coordinate(i,j);
        buttonLabel = colLabels[j] + Integer.toString(i+1);
        BtlButton button = new BtlButton(buttonLabel, currentLocation);
        button.setContentAreaFilled(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        button.setOpaque(true);
        button.setBackground(Color.CYAN);
        
        button.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            setShipCoordinates(button, e);
            button.setBackground(Color.gray);
          }
        });
          
        if (j == 4) {
          BUTTON_NO_WRAP_CONSTRAINTS += ",id player" + buttonLabel;
          shipGrid.add(button, BUTTON_NO_WRAP_CONSTRAINTS ); 
        }
        else {
          BUTTON__WRAP_CONSTRAINTS += ",id player" + buttonLabel;
          shipGrid.add(button, BUTTON__WRAP_CONSTRAINTS);
        }
      }
    }
    return shipGrid;
  }
  
  private JPanel opponentShipGridArea() {
    JPanel shipGrid = new JPanel(new MigLayout("debug, fill"));
    String BUTTON_NO_WRAP_CONSTRAINTS = "wrap, grow, width 120, height 120";
    String BUTTON__WRAP_CONSTRAINTS = "grow, width 120, height 120";
    
    String[] colLabels = {"A","B","C","D","E"};
    String buttonLabel = "";

    for (int i = 0; i < 5; i++) {
      for(int j = 0; j < 5; j++) {
        Coordinate currentLocation = new Coordinate(i,j);
        buttonLabel = colLabels[j] + Integer.toString(i+1);
        BtlButton button = new BtlButton(buttonLabel, currentLocation);
        button.setContentAreaFilled(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        button.setOpaque(true);
        
        button.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            setAttackCoordinates(button, e);         
          }
        });
        
        if (j == 4) {
          BUTTON_NO_WRAP_CONSTRAINTS += ",id opponent" + buttonLabel;
          shipGrid.add(button, BUTTON_NO_WRAP_CONSTRAINTS); 
        }
        else {
          BUTTON__WRAP_CONSTRAINTS += ",id opponent" + buttonLabel;
          shipGrid.add(button, BUTTON__WRAP_CONSTRAINTS);
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
  
  private JPanel checkForPlayagain() {
    JPanel playAgain = new JPanel(new MigLayout("debug, fill"));
    JLabel playerDialog = new JLabel("Would you like to play again?");
    playerDialog.setFont(CAMBRIA_BIGGER);
    playAgain.add(playerDialog, "wrap, align center");
    JButton yesButton = new JButton("Yes");
    JButton noButton = new JButton("No");
    
    yesButton.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent evt) {
        // TODO complete playAgain() method;
      }
    });
    
    noButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        exitGame();
      }
    });
    
    playAgain.add(yesButton, "grow, center, width 60, height 60");
    playAgain.add(noButton, "grow, center, width 60, height 60");

    return playAgain;

  }

  private void exitGame() {
    System.exit(0);
  }

  private void playAgain() {
    eraseBoard();

  }

  private void eraseBoard() {

  }
  
  
  public void registerChatListener(ChatListener listener) {
    this.chatListener = listener;
  }

  public void registerSetShipListener(SetShipListener listener) {
    this.setShipListener = listener;
  }

  public void registerAttackListener(AttackListener listener) {
    this.attackListener = listener;
  }
  
  public Coordinate getAttackCoordinate() {
    return attackCoordinate;
  }

  public void setAttackCoordinate(Coordinate attackCoordinate) {
    this.attackCoordinate = attackCoordinate;
  }
   
  public void display(String message) {
    chatOutput.append(message + "\n");
    scrollChatWindowToBottom();
  }
  
  /**
   * Scrolls the messages text area down to the bottom
   */
  public void scrollChatWindowToBottom() {
    Point bottomScrollPoint = new Point(0, chatOutput.getDocument().getLength());
    chatOutputScrollbar.getViewport().setViewPosition(bottomScrollPoint);
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
   * Emits ChatEvent for controller to catch which includes a string message to send out to the world
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
   * Prompts user for name and returns to controller
   */
  public String getUsername() {
     return JOptionPane.showInputDialog(null, "Enter username: ");
    }
  
  /** 
   * Unlocks the inputs on the game portion of the gui
   * Prompts user for attack
   */
  @Override
  public void yourTurn() {
    displayNotification("It is now your turn");
  };
  
  /**
   * Locks game gui, displays wait message
   */
  @Override
  public void notYourTurn() {
    displayNotification("It is now your opponents turn");
  };
  
  /**
   * Receives ship from controller for user to place
   * @param ship
   */
  @Override
  public void setShip(Ship ship) {
    display("Please place this " + ship.getShipType());
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
        log.info("@@Attempting to set ship: {}", setShipEventShip);
        setShipListener.setShipEventOccurred(sse);
      }   
    }
    setShipEventShip = null;
  };
  
  /**
   * Displays a properly placed ship in our board
   * @param ship
   */
  public void displayShip(Ship ship) {
    
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
    displayNotification("Attacked opponent on " + convertCoordinate(getAttackCoordinate()) + "\n"
        + "It was a " + message.getHitOrMiss());
    if(message.getHitOrMiss().toString() == "HIT") {
      String componentName = "opponent" + convertCoordinate(getAttackCoordinate());
      Component buttonPressed = findComponentByName(gridBoard, componentName);
      
      if (buttonPressed != null) {
        buttonPressed.setBackground(Color.RED);
      }
      
    }
    if(message.getHitOrMiss().toString() == "MISS") {
      
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
    displayNotification("Opponent attacked you on " +  convertCoordinate(message.getCoordinate()) + "\n"
        + "It was a " + message.getHitOrMiss());
    if(message.getShipSunk().toString() != "NONE") {
      displayNotification("Your opponent sunk your " + message.getShipSunk());
    }
  };
  
  /**
   * Resets gui to gameless state
   */
  @Override
  public void resetGame() {};
  
  public String convertCoordinate(Coordinate coord) {
    int x = coord.x;
    int y = coord.y;
    String[] colLabels = {"A","B","C","D","E"};
    
    String convertedCoordinate = colLabels[y] + (x + 1);
    return convertedCoordinate;
  }
  
  public Component findComponentByName(Container container, String componentName) {
    for (Component component: container.getComponents()) {
      if (componentName.equals(component.getName())) {
        return component;
      }
      if (component instanceof JRootPane) {
        // According to the JavaDoc for JRootPane, JRootPane is
        // "A lightweight container used behind the scenes by JFrame,
        // JDialog, JWindow, JApplet, and JInternalFrame.". The reference
        // to the RootPane is set up by implementing the RootPaneContainer
        // interface by the JFrame, JDialog, JWindow, JApplet and
        // JInternalFrame. See also the JavaDoc for RootPaneContainer.
        // When a JRootPane is found, recurse into it and continue searching.
        JRootPane nestedJRootPane = (JRootPane)component;
        return findComponentByName(nestedJRootPane.getContentPane(), componentName);
      }
      if (component instanceof JPanel) {
        // JPanel found. Recursing into this panel.
        JPanel nestedJPanel = (JPanel)component;
        return findComponentByName(nestedJPanel, componentName);
      }
    }
    return null;
  }

  public static void main(String[] args) {
    userInterface btlshypgui = new userInterface();
    Controller controller = new Controller(btlshypgui);
    controller.init();
    controller.playGame();

  }

} // end class

