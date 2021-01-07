public class BroadcastMessage extends ResponseMessage {
  private int senderNameSize;
  private byte[] senderName;

  public BroadcastMessage(int nameSize, int msgSize, byte[] name, byte[] msg) {
    super(MessageBuilder.getBroadcastMessage(), msgSize, msg);
    this.senderName = name;
    this.senderNameSize = nameSize;
  }

  public int getSenderNameSize() {
    return senderNameSize;
  }

  public byte[] getSenderName() {
    return senderName;
  }

  public static int getParamNumber() {
    return 3;
  }
}
