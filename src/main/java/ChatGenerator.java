public class ChatGenerator {
  private static final String ERROR="Error: Please enter the <mode>";
  private static final String USAGE="java -jar <jar-name> <client or server> <port-number> <hostname if using client>";
  private static final String SERVER="server";
  private static final String CLIENT="client";

  public static void main(String[] args) {
    if (args.length < 2) {
      errorMsg();
    }

    String choice = args[0];
    int port = Integer.parseInt(args[1]);

    if (choice.toLowerCase().equals(SERVER)){
      ChatroomServer server = new ChatroomServer(port);
      server.execute();
    } else if (choice.toLowerCase().equals(CLIENT)){
      if (args.length < 3) {
        errorMsg();
      }
      String hostname = args[2];
      ChatroomClient client = new ChatroomClient(hostname, port);
      client.execute();
    } else {
      errorMsg();
    }

  }

  public static void errorMsg(){
    System.out.println(ERROR);
    System.out.println(USAGE);
    System.exit(1);
  }


}
