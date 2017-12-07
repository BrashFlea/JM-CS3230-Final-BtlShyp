package BtlShypUserInterface;

import main.btlshyp.controller.Controller;

public class Main {

  public static void main(String[] args) {
    UserInterface btlshypgui = new UserInterface();
    Controller controller = new Controller(btlshypgui);
    controller.init();
    controller.playGame();


  }

}
