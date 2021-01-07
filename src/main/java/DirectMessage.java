public class DirectMessage extends ResponseMessage {
  private int senderNameSize;
  private byte[] senderName;
  private int receiverNameSize;
  private byte[] receiverName;

  public DirectMessage(int senderSize, int receiverSize, int messageSize, byte[] senderName, byte[] receiverName, byte[] msg) {
    super(MessageBuilder.getDirectMessage(), messageSize, msg);
    this.receiverName = receiverName;
    this.receiverNameSize = receiverSize;
    this.senderName = senderName;
    this.senderNameSize = senderSize;
  }

  public int getSenderNameSize() {
    return senderNameSize;
  }

  public byte[] getSenderName() {
    return senderName;
  }

  public int getReceiverNameSize() {
    return receiverNameSize;
  }

  public byte[] getReceiverName() {
    return receiverName;
  }

  public static int getParamNumber() {
    return 7;
  }
}
