import static org.junit.Assert.*;

import java.nio.charset.StandardCharsets;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class QueryUserRequestTest {

  private QueryUserRequest queryUserRequestTest;
  private byte[] username_expected;

  @Before
  public void setUp() throws Exception {
    this.queryUserRequestTest = (QueryUserRequest) MessageBuilder.buildMessage("22 3 aha");
  }

  @Test
  public void testGetSize(){
    Assert.assertEquals(3, queryUserRequestTest.getSize());
  }

  @Test
  public void testGetUserName(){
    username_expected = queryUserRequestTest.getUsername();
    Assert.assertEquals("aha", new String(username_expected));
  }

  @Test
  public void testGetParamNum(){
    Assert.assertEquals(3, queryUserRequestTest.getParamNumber());
  }
}
