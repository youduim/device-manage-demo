package im.youdu.devicemanagedemo.entity;

import java.util.*;

public class ConstDevice {
    private Map<Integer, String> deviceName;

    public static final int DEFAULT_DEVICE_NUMS = 15;

    public ConstDevice() {
        deviceName = new HashMap<>();
        deviceName.put(0, "Windows");
        deviceName.put(1, "iOS");
        deviceName.put(2, "Pad");
        String keyAndroid = "Android";
        //androidPhone
        deviceName.put(3, "Android");
        //androidPad
        deviceName.put(4, keyAndroid);
        deviceName.put(5, "Mac");
        deviceName.put(6, "Web");
        deviceName.put(7, "wx");
        deviceName.put(8, "RTX");
        //androidHW
        deviceName.put(9, keyAndroid);
        //androidXM
        deviceName.put(10, keyAndroid);
        //androidMZ
        deviceName.put(11, keyAndroid);

        String keyLinux = "Linux";
        //linuxDeb
        deviceName.put(12, keyLinux);
        //linuxRpm
        deviceName.put(13, keyLinux);
    }

    //获取同一平台设备类型Id
    public List<Integer> getSameDeviceType(int deviceType) {
        String name = this.getNameByDeviceType(deviceType);
        List<Integer> list = new ArrayList<>();
        if (name == null) {
            return list;
        }

        for (Map.Entry<Integer, String> entry : deviceName.entrySet()) {
            if (name.equals(entry.getValue())) {
                list.add(entry.getKey());
            }
        }
        return list;
    }

    public Map<Integer, String> getDeviceName() {
        return this.deviceName;
    }

    public String getNameByDeviceType(int deviceType) {
        return this.deviceName.get(deviceType);
    }
}
