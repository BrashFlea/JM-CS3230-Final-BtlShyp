package BtlShypUserInterface;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import main.btlshyp.model.*;

public class BtlButton extends JButton {
  private Coordinate coord;
  
  public BtlButton(Coordinate coord, ActionListener e) {
    this.coord = coord;
    this.actionListener = e;
    
  }
  
  

  
  
  

}
