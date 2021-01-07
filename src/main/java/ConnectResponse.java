public class ConnectResponse extends ResponseMessage {
  private boolean isSuccessful;

  public ConnectResponse(boolean isConnected, int size, byte[] msg) {
    super(MessageBuilder.getConnectResponse(), size, msg);
    this.isSuccessful = isConnected;
  }

  public boolean isSuccessful() {
    return isSuccessful;
  }


  public static int getParamNumber() {
    return 4;
  }
}
