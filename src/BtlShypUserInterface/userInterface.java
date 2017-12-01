package BtlShypUserInterface;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import main.btlshyp.model.Coordinate;
import main.btlshyp.view.View;

import net.miginfocom.swing.MigLayout;

public class userInterface extends View {

  private static final long serialVersionUID = 1L;

  private static JPanel shipGridArea() {
    JPanel shipGrid = new JPanel(new MigLayout("debug, fill"));

    int rows = 5;
    int cols = 5;

    for (int i = 0; i < 5; i++) {
      for(int j = 0; j < 5; j++) {
        JButton button = new JButton(Integer.toString(i+1));
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
    JPanel playAgain = new JPanel(new MigLayout());
    playAgain.add(new JLabel("Would you like to play again?"), "wrap");
    playAgain.add(new JButton("Yes"));
    JButton noButton = new JButton("No");

    noButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        System.exit(0);
      }
    });

    playAgain.add(noButton);

    /*
    yes.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent evt) {
        exitGame();
      }
    });*/

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

        /*
        JFrame playAgainFrame = new JFrame("Example 01");
        playAgainFrame.getContentPane().add(checkForPlayagain());
        playAgainFrame.pack();
        playAgainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        playAgainFrame.setVisible(true);
        */
         
        JFrame shipGridFrame = new JFrame("Jonathan Mirabile - BtlShyp");
        shipGridFrame.getContentPane().add(shipGridArea());
        shipGridFrame.setPreferredSize(new Dimension(750,750));
        shipGridFrame.pack();
        shipGridFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        shipGridFrame.setVisible(true);

      }
    });
  }


} // end class

