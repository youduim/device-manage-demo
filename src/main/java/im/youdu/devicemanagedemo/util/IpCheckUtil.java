package im.youdu.devicemanagedemo.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class IpCheckUtil {
    private static List<IpGroup> ipCache;

    private static final String IP_SPLIT_CHAR = "-";

    /**
     * 检测是否允许设备自动登录
     * 通过ip段检测
     *
     * @param ip   ip信息
     * @param list ip段表 注意，每个String包含了起-止，例如：10.0.0.1-10.0.0.255
     * @return 结果
     */
    public boolean ipCheck(String ip, List<String> list) {
        if (ipCache == null) {
            ipCache = new ArrayList<>();
            for (String str : list) {
                IpGroup ipGroup = new IpGroup(str);
                if (ipGroup.getIpStart() == null) {
                    continue;
                }
                ipCache.add(ipGroup);
            }
        }
        for (IpGroup ipGroup : ipCache) {
            if (isValidRange(ipGroup, ip)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isValidRange(IpGroup ipGroup, String ipToCheck) {
        try {
            long ipLo = ipToLong(InetAddress.getByName(ipGroup.getIpStart()));
            long ipCheck = ipToLong(InetAddress.getByName(ipToCheck));
            if (ipGroup.getIpEnd() == null) {
                return ipCheck >= ipLo;
            }
            long ipHi = ipToLong(InetAddress.getByName(ipGroup.getIpEnd()));

            return (ipCheck >= ipLo && ipCheck <= ipHi);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return false;

        }
    }

    private static long ipToLong(InetAddress ip) {
        long result = 0;
        byte[] ipAdds = ip.getAddress();
        for (byte b : ipAdds) {
            result <<= 8;
            result |= b & 0xff;
        }
        return result;
    }

    class IpGroup {
        String ipStart;
        String ipEnd;

        private IpGroup(String ip) {
            if (ip == null) {
                return;
            }
            if (!ip.contains(IP_SPLIT_CHAR)) {
                ipStart = ip;
                return;
            }
            String[] s = ip.split(IP_SPLIT_CHAR);
            int minSize = 1;
            if (s.length <= minSize) {
                return;
            }
            ipStart = s[0];
            ipEnd = s[1];
        }

        private String getIpStart() {
            return ipStart;
        }

        private String getIpEnd() {
            return ipEnd;
        }

    }
}
