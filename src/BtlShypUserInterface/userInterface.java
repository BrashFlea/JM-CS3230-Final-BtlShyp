package BtlShypUserInterface;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import main.btlshyp.model.Coordinate;
import main.btlshyp.view.View;

import net.miginfocom.swing.MigLayout;

public class userInterface extends View {

  private static final long serialVersionUID = 1L;
  private static final Dimension DIALOG_BUTTON_SIZE = new Dimension(60, 60);
  private static final Dimension GAME_BUTTON_SIZE = new Dimension(120, 120);
  private static final Dimension CHAT_IINPUT_SIZE = new Dimension(250, 150);
  private static final Dimension CHAT_OUTPUT_SIZE = new Dimension(250, 450);
  private static final Font CAMBRIA = new Font("Cambria", Font.PLAIN, 16);
  private static final Font CAMBRIA_BIGGER = new Font("Cambria", Font.PLAIN, 18);
  private static final Font CAMBRIA_BIGGEST = new Font("Cambria", Font.PLAIN, 20);
  private static final ImageIcon BTLSHYP_ICON = new ImageIcon("resources/icons8-battleship-96.png");

  private static JPanel playerShipGridArea() {
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
  
  private static JPanel opponentShipGridArea() {
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
  
  private static JTextPane chatInput() {
    JTextPane chatInput = new JTextPane();
    chatInput.setPreferredSize(CHAT_IINPUT_SIZE);
    
    // Action Listener for "Enter Key"
    
    // Action Listener for "ALT + Enter"
    
    return chatInput;
  }
  
  private static JTextPane chatOutput() {
    JTextPane chatOutput = new JTextPane();
    chatOutput.setPreferredSize(CHAT_OUTPUT_SIZE);
    chatOutput.setEditable(false);
    
    return chatOutput;
  }
  
  /*
  private static JPanel titleHeader() {
    JPanel titleHeader = new JPanel(new MigLayout());
    
  }
  */

  private static JPanel checkForPlayagain() {
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

  private static void exitGame() {
    System.exit(0);
  }

  private void playAgain() {
    eraseBoard();

  }

  private void eraseBoard() {

  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
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
        chatArea.add(chatInput(), "grow");
        
        
        gridBoard.add(playerShipGridArea());
        gridBoard.add(chatArea, "grow");
        gridBoard.add(opponentShipGridArea());
        
        
        shipGridFrame.add(gridBoard);
        shipGridFrame.pack();
        shipGridFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        shipGridFrame.setVisible(true);
        

      }
    });
  }


} // end class

