class Controller implements Messageable {

  public static void main(String[] args) {
    new Controller();
  }

  // this will be replaced by a game for each phone number
  private GameLoader gameLoader;

  private Controller() {
    gameLoader = new GameLoader();
    System.out.println("SimPlant server live!\n");

    // messages from prompt runs on a new thread
    Thread promptServer = new Thread(new PromptServer(this));
    promptServer.start();

    // HTTP server runs on the a new thread
    // incoming messages will initiate their own threads
    Thread httpServer = new Thread(new HttpServer(this));
    httpServer.start();

    try {
      promptServer.join();
      httpServer.join();

    } catch (InterruptedException e) {
      e.printStackTrace();
    }

  }

  @Override
  public String message(String from, String body) {
    // retrieve game from database or whatever
    // and process the command with it
    return gameLoader.getGame(from).processCommand(body);
  }

}
