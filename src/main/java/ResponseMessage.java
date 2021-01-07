public abstract class ResponseMessage extends ChatContent{
  private int messageSize;
  private byte[] message;

  public ResponseMessage(int id, int size, byte[] message) {
    super(id);
    this.message = message;
    this.messageSize = size;
  }

  public int getMessageSize() {
    return messageSize;
  }

  public byte[] getMessage() {
    return message;
  }
}
