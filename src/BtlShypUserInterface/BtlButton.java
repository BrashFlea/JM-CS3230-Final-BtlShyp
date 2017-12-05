package BtlShypUserInterface;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import main.btlshyp.model.*;

public class BtlButton extends JButton {
  private Coordinate buttonCoordinate;
  
  public BtlButton(String buttonText, Coordinate coord) {
    super(buttonText);
    this.buttonCoordinate = coord;
  }
  
  public Coordinate getCoordinates() {
    return this.buttonCoordinate;
  }
  
}
