package BtlShypUserInterface;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import main.btlshyp.model.Coordinate;
import main.btlshyp.view.View;

public class userInterface extends View {
  
  private static final long serialVersionUID = 1L;
  private JPanel mainPanel = new JPanel();
  private JFrame playAgain;

  private void initUI() {
    displayMainArea();
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
                 sendAttack(e.getSource());
              }
          });
        pane.add(button);
      }

    }
    pane.setVisible(true); 
  }
 
  
  
  private void checkForPlayagain() {
    playAgain = new JFrame();
    playAgain.setLayout(new FlowLayout());
    playAgain.setVisible(true);
    JButton yes = new JButton("Yes");
    JButton no = new JButton("No");
    JLabel playAgainLabel = new JLabel("Would you like to play again?");
    playAgain.add(yes);
    playAgain.add(no);
    
    no.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent evt) {
        System.exit(0);
      }
    });

    yes.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent evt) {
        exitGame();
      }
    });
    
    playAgain.setTitle("Play Again?");
    playAgain.setDefaultCloseOperation(EXIT_ON_CLOSE);
    playAgain.pack();
    
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
    userInterface test = new userInterface();
    test.initUI();

  }
  

} // end class

