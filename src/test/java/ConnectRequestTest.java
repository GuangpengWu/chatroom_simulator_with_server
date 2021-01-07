import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ConnectRequestTest {

  private ConnectRequest connectRequestTest;
  private byte[] username_expected;

  @Before
  public void setUp() throws Exception {
    this.connectRequestTest = (ConnectRequest) MessageBuilder.buildMessage("19 3 aha");
  }

  @Test
  public void testGetUserNameSize(){
    Assert.assertEquals(3, connectRequestTest.getUsernameSize());
  }

  @Test
  public void testGetUserName(){
    username_expected = connectRequestTest.getUsername();
    Assert.assertEquals("aha", new String(username_expected));
  }

  @Test
  public void testGetParamNum(){
    Assert.assertEquals(3, connectRequestTest.getParamNumber());
  }

}
