package BtlShypUserInterface;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import main.btlshyp.model.*;

public class AttackButton extends JButton {
  private Coordinate coord;
  
  public AttackButton(Coordinate coord, ActionListener e) {
    this.coord = coord;
    this.actionListener = e;
    
  }

  
  
  

}
