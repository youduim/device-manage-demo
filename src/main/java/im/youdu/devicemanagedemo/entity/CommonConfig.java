package im.youdu.devicemanagedemo.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "config")
public class CommonConfig {
    /**
     * 授权方式
     */
    private int authMethod;

    /**
     * 是否自动授权
     */
    private boolean autoAuth;

    /**
     * 允许的ip段
     * 在开启自动授权时生效
     */
    private List<String> ipList;

    private List<String> whiteList;

    /**
     * 限定各端设备数
     * 在开启自动授权时生效
     */
    private Map<String, Integer> deviceNums;

    /**
     * 第三方授权认证Url
     * 使用第三分授权认证时生效
     */
    private String thirdAuthUrl;

    @Override
    public String toString() {
        return "CommonConfig{" +
                "authMethod=" + authMethod +
                ", autoAuth=" + autoAuth +
                ", ipList=" + ipList +
                ", whiteList=" + whiteList +
                ", deviceNums=" + deviceNums +
                ", thirdAuthUrl='" + thirdAuthUrl + '\'' +
                '}';
    }

    public String getThirdAuthUrl() {
        return thirdAuthUrl;
    }

    public void setThirdAuthUrl(String thirdAuthUrl) {
        this.thirdAuthUrl = thirdAuthUrl;
    }

    public int getAuthMethod() {
        return authMethod;
    }

    public void setAuthMethod(int authMethod) {
        this.authMethod = authMethod;
    }

    public boolean isAutoAuth() {
        return autoAuth;
    }

    public void setAutoAuth(boolean autoAuth) {
        this.autoAuth = autoAuth;
    }

    public List<String> getIpList() {
        return ipList;
    }

    public void setIpList(List<String> ipList) {
        this.ipList = ipList;
    }

    public Map<String, Integer> getDeviceNums() {
        return deviceNums;
    }

    public void setDeviceNums(Map<String, Integer> deviceNums) {
        this.deviceNums = deviceNums;
    }

    public List<String> getWhiteList() {
        return whiteList;
    }

    public void setWhiteList(List<String> whiteList) {
        this.whiteList = whiteList;
    }
}
