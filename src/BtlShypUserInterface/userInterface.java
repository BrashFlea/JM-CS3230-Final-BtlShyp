package BtlShypUserInterface;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import main.btlshyp.model.Coordinate;
import main.btlshyp.view.View;

import net.miginfocom.swing.MigLayout;;

public class userInterface extends View {
  
  private static final long serialVersionUID = 1L;
  private JFrame playAgain;
  JPanel panel = new JPanel(new MigLayout());

  private void initUI() {
    //displayMainArea();
    checkForPlayagain();
  }
  
  private void displayMainArea() {
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setPreferredSize(new Dimension(425,545));
    setLayout(new GridLayout(5,5));
    setVisible(true);
    setTitle("Jonathan Mirabile - BtlShyp");
    setResizable(true);
    
    int rows = 5;
    int cols = 5;
    Container pane = getContentPane();
    pane.setLayout(new GridLayout(rows, cols));
    
    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; i++) {
        AttackButton button = new AttackButton(new Coordinate(i,j), 
            new ActionListener() {
              public void actionPerformed(ActionEvent e) 
              {
                 //sendAttack(e.getSource());
              }
          });
        pane.add(button);
      }

    }
    pane.setVisible(true); 
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

        JFrame frame = new JFrame("Example 01");
        frame.getContentPane().add(checkForPlayagain());
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
      }
    });
}
  

} // end class

