package im.youdu.devicemanagedemo.entity;

public class Const {
    /**
     * 授权禁止登录
     */
    public static final int AUTH_STATUS_FAIL = -1;
    /**
     * 授权允许登录
     */
    public static final int AUTH_STATUS_SUCCESS = 0;
    /**
     * 授权等待
     */
    public static final int AUTH_STATUS_WAIT = 1;
    /**
     * 已自动授权
     */
    public static final int AUTH_STATUS_AUTO = 2;

    /**
     * 第三方验证返回code
     */
    public static final String THIRD_RESPONSE_CODE = "code";

    /**
     * 第三方验证返回msg
     */
    public static final String THIRD_RESPONSE_MSG = "msg";


}
