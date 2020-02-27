package im.youdu.devicemanagedemo.entity;

public class StaticParam {
    private StaticParam(){

    }

    /**
     * 第三方url验证
     */
    private static boolean thirdAuthUrlCheck;

    public static boolean isThirdAuthUrlCheck() {
        return thirdAuthUrlCheck;
    }

    public static void setThirdAuthUrlCheck(boolean thirdAuthUrlCheck) {
        StaticParam.thirdAuthUrlCheck = thirdAuthUrlCheck;
    }
}
