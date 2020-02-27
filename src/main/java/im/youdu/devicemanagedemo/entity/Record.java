package im.youdu.devicemanagedemo.entity;

import com.alibaba.fastjson.JSONObject;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "record")
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int buin;
    private int gid;
    private String account;
    private String cnName;
    private int gender;
    private int deptId;
    private String mobile;
    private String phone;
    private String email;
    private int deviceType;
    private String deviceId;
    private Date recordTime;
    private String mac;
    private String ip;


    public Record() {
    }

    public Record(JSONObject jsonObject) {
        if (jsonObject==null){
            return;
        }
        this.buin = jsonObject.getInteger("buin");
        this.deviceType = jsonObject.getInteger("deviceType");
        this.deviceId = jsonObject.getString("deviceId");
        this.mac = jsonObject.getString("mac");
        this.ip = jsonObject.getString("ipAddress");
        String ipOther = ":";
        if (this.ip != null && this.ip.contains(ipOther)) {
            this.ip = this.ip.substring(0, this.ip.indexOf(ipOther));
        }

        JSONObject userInfo = jsonObject.getJSONObject("userInfo");
        this.gid = userInfo.getInteger("gid");
        this.account = userInfo.getString("account");
        this.cnName = userInfo.getString("chsName");
        this.gender = userInfo.getInteger("gender");
        this.deptId = userInfo.getInteger("orgId");
        this.mobile = userInfo.getString("mobile");
        this.phone = userInfo.getString("phone");
        this.email = userInfo.getString("email");

        this.recordTime = new Date();
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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
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


    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", buin=" + buin +
                ", gid=" + gid +
                ", account='" + account + '\'' +
                ", cnName='" + cnName + '\'' +
                ", gender='" + gender + '\'' +
                ", deptId=" + deptId +
                ", mobile='" + mobile + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", deviceType=" + deviceType +
                ", deviceId='" + deviceId + '\'' +
                ", recordTime=" + recordTime +
                ", mac='" + mac + '\'' +
                ", ip='" + ip + '\'' +
                '}';
    }
}
