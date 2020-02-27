package im.youdu.devicemanagedemo.util;

public class PageUtil {
    public static final int DEFAULT_PAGE_SIZE = 10;

    public static int getPageNum(String str) {
        try {
            int pageNum = Integer.parseInt(str);
            if (pageNum <= 0) {
                return 1;
            }
            return pageNum;
        } catch (NumberFormatException e) {
            return 1;
        }
    }

    public static int getPageSize(String str, int defaultSize) {
        try {
            int pageNum = Integer.parseInt(str);
            if (pageNum <= 0) {
                return defaultSize;
            }
            return pageNum;
        } catch (NumberFormatException e) {
            return defaultSize;
        }
    }
}
