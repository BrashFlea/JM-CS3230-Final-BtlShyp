package BtlShypUserInterface;

import lombok.extern.slf4j.Slf4j;
import main.btlshyp.controller.Controller;
import static main.btlshyp.Main.SERVER_IP_ADDRESS;
import static main.btlshyp.Main.SERVER_PORT;

@Slf4j
public class Main {

  private static final String LOG_FORMAT = "%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS %4$-6s [%2$s] %5$s%6$s%n";

  /**
   * The main entry point for BtlShyp!
   *
   * @param args Accepts an (optional) server IP address followed by an (optional) server port
   */
  public static void main(String[] args) {
    System.setProperty("java.util.logging.SimpleFormatter.format", LOG_FORMAT);
    log.info("Application Starting");

    if (args.length > 0) {
      handleCommandlineArgs(args);
    }

    UserInterface jmBtlshypgui = new UserInterface();
    Controller controller = new Controller(jmBtlshypgui);
    controller.init();
    controller.playGame();
  }

  /**
   * Handles the commandline arguments.
   */
  private static void handleCommandlineArgs(String[] args) {
    log.info("Commandline arguments provided");

    SERVER_IP_ADDRESS = args[0];
    log.info("Server ip address: {}", SERVER_IP_ADDRESS);

    if (args.length > 1) {
      SERVER_PORT = Integer.parseInt(args[1]);
      log.info("Server port: {}", SERVER_PORT);
    }
  }

}
