package BtlShypUserInterface;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import main.btlshyp.controller.Controller;
import main.btlshyp.message.AttackResponseMessage;
import main.btlshyp.model.Coordinate;
import main.btlshyp.model.Ship;
import main.btlshyp.view.DefaultView;
import main.btlshyp.view.View;
import main.btlshyp.view.event.AttackListener;
import main.btlshyp.view.event.ChatEvent;
import main.btlshyp.view.event.ChatListener;
import main.btlshyp.view.event.SetShipListener;
import net.miginfocom.swing.MigLayout;

public class userInterface extends View {

  private static final long serialVersionUID = 1L;
  private static final Dimension SEND_BUTTON_SIZE = new Dimension(250, 35);
  private static final Dimension DIALOG_BUTTON_SIZE = new Dimension(60, 60);
  private static final Dimension GAME_BUTTON_SIZE = new Dimension(120, 120);
  private static final Dimension CHAT_IINPUT_SIZE = new Dimension(250, 115);
  private static final Dimension CHAT_OUTPUT_SIZE = new Dimension(250, 450);
  private static final Font CAMBRIA = new Font("Cambria", Font.PLAIN, 16);
  private static final Font CAMBRIA_BIGGER = new Font("Cambria", Font.PLAIN, 18);
  private static final Font CAMBRIA_BIGGEST = new Font("Cambria", Font.PLAIN, 20);
  private static final ImageIcon BTLSHYP_ICON = new ImageIcon("resources/icons8-battleship-96.png");
  
  private static JTextArea chatInput;
  private static JTextArea chatOutput;

  public userInterface() {
    initUI();
  }
  
  private void initUI() {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    
    JFrame playAgainFrame = new JFrame("BtlShyp");
    playAgainFrame.getContentPane().add(checkForPlayagain());
    playAgainFrame.setIconImage(BTLSHYP_ICON.getImage());
    playAgainFrame.pack();
    playAgainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    playAgainFrame.setVisible(true);
    
     
    JFrame shipGridFrame = new JFrame("Jonathan Mirabile - BtlShyp");
    shipGridFrame.setIconImage(BTLSHYP_ICON.getImage());
    JPanel gridBoard = new JPanel(new MigLayout("debug", "[][grow][]"));
    JPanel chatArea = new JPanel(new MigLayout("debug, fill"));
    
    chatArea.add(chatOutput(), "wrap, grow");
    chatArea.add(chatInput(), "wrap, grow");
    chatArea.add(sendButton(), "grow");
    
    
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

    String[] colLabels = {"A","B","C","D","E"};
    String buttonLabel = "";

    for (int i = 0; i < 5; i++) {
      for(int j = 0; j < 5; j++) {
        buttonLabel = colLabels[j] + Integer.toString(i+1);
        //TODO Change to BtlButtons and pass in coordinates
        JButton button = new JButton(buttonLabel);
        button.setPreferredSize(GAME_BUTTON_SIZE);
        
        button.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            //button.setText("P");          
          }
        });
          
        if (j == 4) {
          shipGrid.add(button, "wrap, grow"); 
        }
        else {
          shipGrid.add(button, "grow");
        }
      }
    }
    return shipGrid;
  }
  
  private JPanel opponentShipGridArea() {
    JPanel shipGrid = new JPanel(new MigLayout("debug, fill"));
    
    String[] colLabels = {"A","B","C","D","E"};
    String buttonLabel = "";

    for (int i = 0; i < 5; i++) {
      for(int j = 0; j < 5; j++) {
        buttonLabel = colLabels[j] + Integer.toString(i+1);
        JButton button = new JButton(buttonLabel);
        button.setPreferredSize(GAME_BUTTON_SIZE);
        
        if (j == 4) {
          shipGrid.add(button, "wrap, grow"); 
        }
        else {
          shipGrid.add(button, "grow");
        }
      }
    }
    return shipGrid;
  }
  
  private JTextArea chatInput() {
    chatInput = new JTextArea();
    chatInput.setPreferredSize(CHAT_IINPUT_SIZE);

    // Action Listener for "Enter"
    chatInput.addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        if((e.getKeyCode() == KeyEvent.VK_ENTER)) {
          //sendChat(e);
        }
      }
    });

    // Action Listener for "ALT + Enter"
    chatInput.addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        if((e.getKeyCode() == KeyEvent.VK_ENTER && e.getModifiers() == KeyEvent.CTRL_MASK)) {
          //sendChat(e);
        }
      }
    });

    return chatInput;
  }
  
  private JButton sendButton() {
    JButton sendButton = new JButton("Send");
    sendButton.setPreferredSize(SEND_BUTTON_SIZE);
    
    sendButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        sendChat(e);
      }
    });
    
    return sendButton;
  }
  
  private JTextArea chatOutput() {
    chatOutput = new JTextArea();
    chatOutput.setPreferredSize(CHAT_OUTPUT_SIZE);
    chatOutput.setEditable(false);
    
    return chatOutput;
  }
  
  /*
  private JPanel titleHeader() {
    JPanel titleHeader = new JPanel(new MigLayout());
    
  }
  */

  private JPanel checkForPlayagain() {
    JPanel playAgain = new JPanel(new MigLayout("debug, fill"));
    JLabel playerDialog = new JLabel("Would you like to play again?");
    playerDialog.setFont(CAMBRIA_BIGGER);
    playAgain.add(playerDialog, "wrap, align center");
    JButton yesButton = new JButton("Yes");
    JButton noButton = new JButton("No");
    
    yesButton.setPreferredSize(DIALOG_BUTTON_SIZE);
    noButton.setPreferredSize(DIALOG_BUTTON_SIZE);
  
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
    
    playAgain.add(yesButton, "grow, center");
    playAgain.add(noButton, "grow, center");

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
  
  public void display(String message) {
    chatOutput.append(message + "\n");
  }
  
  /**
   * Displays the results of your attack attempt on your opponent. Analogous to putting a pin in the 
   * vertical portion of the traditional board game.
   * @param hitOrMiss
   * @param coordinate
   */
  public void displayAttack(AttackResponseMessage message) {};
  
  /**
   * Displays the results of an opponent's attack on you. Analogous to putting a pin in a boat
   * or the sea on the horizontal part of the traditional board game.
   * @param hitOrMiss
   * @param coordinate
   */
  public void displayOpponentAttack(AttackResponseMessage message) {};
  
  /**
   * Displays community chat messages that are broadcast to all
   * @param chat
   */
  public void displayChat(String user, String chat) {
    display(user + ": " + chat);
  };
  
  /**
   * Displays notifications unique to the user, such as "Please re-place your boats", "Waiting for opponent", etc
   */
  public void displayNotification(String text) {};
  
  /**
   * Sends controller a coordinate to attack
   */
  public void sendAttack(ActionEvent e) {};
  
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
  public void yourTurn() {};
  
  /**
   * Locks game gui, displays wait message
   */
  public void notYourTurn() {};
  
  /**
   * Receives ship from controller for user to place
   * @param ship
   */
  public void setShip(Ship ship) {
    this.shipToPlace = ship;
  };
  
  /**
   *Displays a properly placed ship in our board
   * @param ship
   */
  public void displayShip(Ship ship) {};
  
  /**
   * Collects coordinates into a ship and emits a setShipEvent
   */
  public void attemptSetShip(ActionEvent e) {};
  /**
   * Resets gui to gameless state
   */
  public void resetGame() {};

  public static void main(String[] args) {
    userInterface testUserInterface = new userInterface();
    Controller controller = new Controller(testUserInterface);
    controller.init();
    controller.playGame();
    
    

  }


} // end class

