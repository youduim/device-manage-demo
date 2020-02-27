package im.youdu.devicemanagedemo.entity;

import com.alibaba.fastjson.JSONObject;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.Map;

public class Response {
    public static JSONObject getAllowResponse() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("errcode", 0);
        jsonObject.put("errmsg", "allow");
        return jsonObject;
    }

    public static JSONObject getForbidResponse() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("errcode", -1);
        jsonObject.put("errmsg", "forbid");
        return jsonObject;
    }

    public static JSONObject getForbidResponse(String msg) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("errcode", -1);
        jsonObject.put("errmsg", msg);
        return jsonObject;
    }

    public static JSONObject getWaitAdminResponse() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("errcode", 2);
        jsonObject.put("errmsg", "need admin check");
        return jsonObject;
    }

    public static JSONObject getSmsResponse() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("errcode", 1);
        jsonObject.put("errmsg", "need sms verify code");
        return jsonObject;
    }

    public static JSONObject getSuccessResponse() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("errcode", 0);
        jsonObject.put("errmsg", "ok");
        return jsonObject;
    }

    public static JSONObject getFailResponse() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("errcode", -1);
        jsonObject.put("errmsg", "option fail");
        return jsonObject;
    }

    public static JSONObject getFailResponse(String msg) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("errcode", -1);
        jsonObject.put("errmsg", msg);
        return jsonObject;
    }

    public static JSONObject getPageResponse(Page page) {
        JSONObject jsonObject = getSuccessResponse();
        Map<String, Object> map = new HashMap<>(4);
        if (page == null) {
            jsonObject.put("data", map);
            return jsonObject;
        }
        map.put("content", page.getContent());
        map.put("totalPage", page.getTotalPages());
        map.put("pageNum", page.getNumber());
        map.put("pageSize", page.getSize());
        jsonObject.put("data", map);
        return jsonObject;
    }
}
