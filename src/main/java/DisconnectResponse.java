public class DisconnectResponse extends ResponseMessage {

  public DisconnectResponse(int size, byte[] message) {
    super(MessageBuilder.getConnectResponse(), size, message);
  }

  public static int getParamNumber() {
    return 3;
  }

}

