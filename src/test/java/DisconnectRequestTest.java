import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DisconnectRequestTest {

  private DisconnectRequest disConnectRequestTest;
  private byte[] username_expected;

  @Before
  public void setUp() throws Exception {
    this.disConnectRequestTest = (DisconnectRequest) MessageBuilder.buildMessage("21 3 aha");
  }

  @Test
  public void testGetUserNameSize(){
    Assert.assertEquals(3, disConnectRequestTest.getSize());
  }

  @Test
  public void testGetUserName(){
    username_expected = disConnectRequestTest.getUsername();
    Assert.assertEquals("aha", new String(username_expected));
  }

  @Test
  public void testGetParamNum(){
    Assert.assertEquals(3, disConnectRequestTest.getParamNumber());
  }
}
