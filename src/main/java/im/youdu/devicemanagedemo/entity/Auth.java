package im.youdu.devicemanagedemo.entity;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "auth")
public class Auth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int buin;
    private int gid;
    private int deviceType;
    private String deviceId;
    private String mac;
    private String ip;
    private int status;
    private Date updateTime;
    private Date createTime;

    public Auth() {
    }

    /**
     * 注意 Auth status 值为1
     *
     * @param record 申请记录
     */
    public Auth(Record record) {
        this.buin = record.getBuin();
        this.gid = record.getGid();
        this.deviceType = record.getDeviceType();
        this.deviceId = record.getDeviceId();
        this.mac = record.getMac();
        this.ip = record.getIp();
        this.status = Const.AUTH_STATUS_WAIT;
    }

    @Override
    public String toString() {
        return "Auth{" +
                "id=" + id +
                ", buin=" + buin +
                ", gid=" + gid +
                ", deviceType=" + deviceType +
                ", deviceId='" + deviceId + '\'' +
                ", mac='" + mac + '\'' +
                ", ip='" + ip + '\'' +
                ", status=" + status +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBuin() {
        return buin;
    }

    public void setBuin(int buin) {
        this.buin = buin;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getStatus() {
        return status;
    }


    public void setStatus(int status) {
        this.status = status;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
