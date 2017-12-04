package BtlShypUserInterface;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import main.btlshyp.model.Coordinate;
import main.btlshyp.view.View;

import net.miginfocom.swing.MigLayout;

public class userInterface extends View {

  private static final long serialVersionUID = 1L;

  private static JPanel playerShipGridArea() {
    JPanel shipGrid = new JPanel(new MigLayout("debug, fill"));

    String[] colLabels = {"A","B","C","D","E"};
    String buttonLabel = "";

    for (int i = 0; i < 5; i++) {
      for(int j = 0; j < 5; j++) {
        buttonLabel = colLabels[j] + Integer.toString(i+1);
        //TODO Change to AttackButtons and pass in coordinates
        JButton button = new JButton(buttonLabel);
        
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

  private static JPanel checkForPlayagain() {
    JPanel playAgain = new JPanel(new MigLayout("debug, fill"));
    playAgain.add(new JLabel("Would you like to play again?"), "wrap, center");
    JButton yesButton = new JButton("Yes");
    JButton noButton = new JButton("No");
  
    yesButton.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent evt) {
        //playAgain();
      }
    });
    
    noButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        System.exit(0);
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

  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        try {
          UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
          ex.printStackTrace();
        }

        
        JFrame playAgainFrame = new JFrame("Jonathan Mirabile - BtlShyp");
        playAgainFrame.getContentPane().add(checkForPlayagain());
        playAgainFrame.pack();
        playAgainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        playAgainFrame.setVisible(true);
        
         
        JFrame shipGridFrame = new JFrame("Jonathan Mirabile - BtlShyp");
        JPanel gridBoard = new JPanel(new MigLayout("debug"));
        //shipGridFrame.setPreferredSize(new Dimension(750,750));
        
        gridBoard.add(playerShipGridArea());
        gridBoard.add(opponentShipGridArea());
        
        
        shipGridFrame.add(gridBoard);
        shipGridFrame.pack();
        shipGridFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        shipGridFrame.setVisible(true);
        

      }
    });
  }


} // end class

