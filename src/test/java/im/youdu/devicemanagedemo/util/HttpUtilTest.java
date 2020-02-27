package im.youdu.devicemanagedemo.util;

import org.junit.Test;

public class HttpUtilTest {

    @Test
    public void testCheck() {
        boolean result = HttpUtil.checkUrl("http://");
        System.out.println(result);
        result = HttpUtil.checkUrl("http://www.baidu");
        System.out.println(result);
        result = HttpUtil.checkUrl("https://www.baidu.com");
        result = HttpUtil.checkUrl("https://youdu.im");
        System.out.println(result);
    }
}
