package im.youdu.devicemanagedemo.util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class IpCheckUtilTest {

    @Test
    public void testIsValidRange() {
        //boolean result = IpCheckUtil.isValidRange("192.168.4.1", "192.170.45.112", "192.169.42.25");
        //System.out.println(result);
        String ip1 = "10.0.0.1-10.0.0.254";
        String ip2 = "127.0.0.1-127.0.0.255";
        String ip3 = "127.0.0.1";
        IpCheckUtil ipCheckUtil = new IpCheckUtil();
        List<String> list = new ArrayList<>();
        list.add(ip1);
        list.add(ip3);
        boolean result = ipCheckUtil.ipCheck("10.0.0.111", list);
        System.out.println(result);
        result = ipCheckUtil.ipCheck("9.0.0.1", list);
        System.out.println(result);
        result = ipCheckUtil.ipCheck("10.1.0.255", list);
        System.out.println(result);
        result = ipCheckUtil.ipCheck("10.0.1.0", list);
        System.out.println(result);
        String ip = "127.0.0.1:51056";
        System.out.println(ip.substring(0, ip.indexOf(":")));
        //result = ipCheckUtil.ipCheck("127.0.0.1:51056", list);
        //System.out.println(result);
        result = ipCheckUtil.ipCheck("127.0.0.1", list);
        System.out.println(result);

    }
}
