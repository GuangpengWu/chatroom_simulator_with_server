public class FailedMessage extends ResponseMessage {

  public FailedMessage(int size, byte[] msg) {
    super(MessageBuilder.getFailedMessage(), size, msg);
  }

  public static int getParamNumber() {
    return 3;
  }
}
