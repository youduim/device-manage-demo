package im.youdu.devicemanagedemo.entity;


public class Device {
    private int deviceType;
    private String deviceName;
    private int deviceNum;

    public Device(int deviceType, String deviceName, int deviceNum) {
        this.deviceType = deviceType;
        this.deviceName = deviceName;
        this.deviceNum = deviceNum;
    }

    @Override
    public String toString() {
        return "Device{" +
                "deviceType=" + deviceType +
                ", deviceName='" + deviceName + '\'' +
                ", deviceNum=" + deviceNum +
                '}';
    }

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public int getDeviceNum() {
        return deviceNum;
    }

    public void setDeviceNum(int deviceNum) {
        this.deviceNum = deviceNum;
    }
}
