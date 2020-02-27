package im.youdu.devicemanagedemo.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HttpUtil {
    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    private static final String GET_STRING = "GET";

    /**
     * 发送http/https get post请求
     *
     * @param requestUrl 请求地址
     * @param method     请求方式
     * @param params     参数
     * @return 请求结果
     */
    public static String sendRequest(String requestUrl, String method, Map<String, String> params) {
        if (requestUrl == null) {
            return null;
        }
        requestUrl = requestUrl.trim();
        boolean https = false;
        String httpsStart = "https://";
        if (requestUrl.startsWith(httpsStart)) {
            https = true;
        }
        StringBuilder buffer = new StringBuilder();
        try {
            if (null != params && params.size() > 0) {
                if (GET_STRING.equalsIgnoreCase(method)) {
                    requestUrl += setParam(requestUrl, params);
                }
            }
            SSLSocketFactory ssf = null;
            if (https) {
                //创建SSLContext
                SSLContext sslContext = SSLContext.getInstance("SSL");
                TrustManager[] tm = {new MyX509TrustManager()};
                //初始化
                sslContext.init(null, tm, new java.security.SecureRandom());
                //获取SSLSocketFactory对象
                ssf = sslContext.getSocketFactory();
            }

            URL url = new URL(requestUrl);
            InputStream is;
            if (https) {
                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setUseCaches(false);
                conn.setRequestMethod(method);

                conn.setConnectTimeout(10000);

                if (ssf != null) {
                    //设置当前实例使用的SSLSoctetFactory
                    conn.setSSLSocketFactory(ssf);
                }
                conn.connect();
                //往服务器端写内容
                postParam(params, method, requestUrl, conn.getOutputStream());
                //读取服务器端返回的内容
                is = conn.getInputStream();
            } else {
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setUseCaches(false);
                conn.setRequestMethod(method);
                conn.setConnectTimeout(10000);

                conn.connect();
                //往服务器端写内容
                postParam(params, method, requestUrl, conn.getOutputStream());
                is = conn.getInputStream();
            }


            InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                buffer.append(line);
            }
        } catch (Exception e) {
            logger.error("request error,msg:{}", e.getMessage());
        }
        return buffer.toString();
    }

    public static boolean checkUrl(String urlString) {
        try {
            URL url = new URL(urlString);
            url.openStream();
            return true;
        } catch (Exception e) {
            logger.info("check url false,exception:{}", e.getMessage());
            return false;
        }
    }

    private static void postParam(Map<String, String> params, String method, String requestUrl, OutputStream os) throws IOException {
        //往服务器端写内容
        if (null != params && params.size() > 0) {
            if (!GET_STRING.equalsIgnoreCase(method)) {
                os.write(setParam(requestUrl, params).getBytes(StandardCharsets.UTF_8));
                os.close();
            }
        }
    }

    private static String setParam(String requestUrl, Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder requestParam;
        String splitChar = "?";
        if (requestUrl.contains(splitChar)) {
            requestParam = new StringBuilder("&");
        } else {
            requestParam = new StringBuilder("?");
        }
        for (String key : params.keySet()) {
            String value = params.get(key);
            requestParam.append(key);
            requestParam.append("=");
            requestParam.append(URLEncoder.encode(value, StandardCharsets.UTF_8.toString()));
            requestParam.append("&");
        }
        requestParam.deleteCharAt(requestParam.length() - 1);
        return requestParam.toString();
    }

}
