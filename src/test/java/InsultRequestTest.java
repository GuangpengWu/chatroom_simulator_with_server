import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class InsultRequestTest {

  private InsultRequest insultRequestTest;
  private byte[] sender_expected;
  private byte[] receiver_expected;

  @Before
  public void setUp() throws Exception {
    this.insultRequestTest = (InsultRequest) MessageBuilder.buildMessage("27 2 aa 3 aha");
  }

  @Test
  public void testGetSenderNameSize(){
    Assert.assertEquals(2, insultRequestTest.getSenderNameSize());
  }

  @Test
  public void testGetReceiverNameSize(){
    Assert.assertEquals(3, insultRequestTest.getReceiverNameSize());
  }

  @Test
  public void testGetSenderName(){
    sender_expected = insultRequestTest.getSenderName();
    Assert.assertEquals("aa", new String(sender_expected));
  }

  @Test
  public void testGetReceiverName(){
    receiver_expected = insultRequestTest.getReceiverName();
    Assert.assertEquals("aha", new String(receiver_expected));
  }

  @Test
  public void testGetParamNum(){
    Assert.assertEquals(5, insultRequestTest.getParamNumber());
  }
}
