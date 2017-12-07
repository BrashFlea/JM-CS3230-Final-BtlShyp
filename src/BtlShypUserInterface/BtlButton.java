package BtlShypUserInterface;

import javax.swing.JButton;
import main.btlshyp.model.*;

public class BtlButton extends JButton {
  /**
   * Extension of the JButton object that allows coordinates to be stored within the button directly
   * Author: Jonathan Mirabile
   * Date: 12/7/2017
   */
  private static final long serialVersionUID = 1L;
  private Coordinate buttonCoordinate;
  
  public BtlButton(String buttonText, Coordinate coord) {
    super(buttonText);
    this.buttonCoordinate = coord;
  }
  
  public Coordinate getCoordinates() {
    return this.buttonCoordinate;
  }
  
}
