import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BroadcastMessageTest {

  private BroadcastMessage broadcastMessageTest;
  private byte[] sender;
  private byte[] msg;

  @Before
  public void setUp() throws Exception {
    this.broadcastMessageTest = (BroadcastMessage) MessageBuilder.buildMessage("24 2 aa 4 ahha");
  }

  @Test
  public void testSenderUserNameSize(){
    Assert.assertEquals(2, broadcastMessageTest.getSenderNameSize());
  }

  @Test
  public void testGetSenderName(){
    sender = broadcastMessageTest.getSenderName();
    Assert.assertEquals("aa", new String(sender));
  }

  @Test
  public void testGetParamNum(){
    Assert.assertEquals(3, broadcastMessageTest.getParamNumber());
  }
}
