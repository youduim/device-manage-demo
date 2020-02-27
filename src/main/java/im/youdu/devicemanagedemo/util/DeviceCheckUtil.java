package im.youdu.devicemanagedemo.util;

import im.youdu.devicemanagedemo.entity.ConstDevice;

import java.util.Map;

public class DeviceCheckUtil {
    /**
     * 检测是否允许设备自动登录
     * 通过设备数检测
     *
     * @param deviceType 请求记录
     * @param deviceNums 规则表
     * @return 结果
     */
    public static boolean deviceCheck(int deviceType, long alreadyAuthSize, Map<String, Integer> deviceNums) {
        ConstDevice constDevice = new ConstDevice();

        String deviceName = constDevice.getNameByDeviceType(deviceType);
        if (deviceName == null) {
            return false;
        }

        int num = deviceNums.getOrDefault(deviceName, ConstDevice.DEFAULT_DEVICE_NUMS);
        if (num == 0) {
            return false;
        }
        return num > alreadyAuthSize;
    }
}
