import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DirectMessageTest {

  private DirectMessage directMessageTest;
  private byte[] sender;
  private byte[] receiver;
  private byte[] msg;

  @Before
  public void setUp() throws Exception {
    this.directMessageTest = (DirectMessage) MessageBuilder.buildMessage("25 2 aa 3 aha 4 ahha");
  }

  @Test
  public void testSenderUserNameSize(){
    Assert.assertEquals(2, directMessageTest.getSenderNameSize());
  }

  @Test
  public void testReceiverNameSize(){
    Assert.assertEquals(3, directMessageTest.getReceiverNameSize());
  }

  @Test
  public void testGetSenderName(){
    sender = directMessageTest.getSenderName();
    Assert.assertEquals("aa", new String(sender));
  }

  @Test
  public void testGetReceiverName(){
    receiver = directMessageTest.getReceiverName();
    Assert.assertEquals("aha", new String(receiver));
  }

  @Test
  public void testGetParamNum(){
    Assert.assertEquals(7, directMessageTest.getParamNumber());
  }
}
