package im.youdu.devicemanagedemo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import im.youdu.devicemanagedemo.entity.*;
import im.youdu.devicemanagedemo.service.AuthServiceI;
import im.youdu.devicemanagedemo.service.RecordServiceI;
import im.youdu.devicemanagedemo.util.DeviceCheckUtil;
import im.youdu.devicemanagedemo.util.HttpUtil;
import im.youdu.devicemanagedemo.util.IpCheckUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequestMapping()
@Controller
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    @Resource
    private RecordServiceI recordService;

    @Resource
    private AuthServiceI authService;

    @Resource
    private CommonConfig commonConfig;

    @PostMapping("device/auth")
    @ResponseBody
    public JSONObject receive(HttpServletRequest request) throws IOException {
        JSONObject jsonObject = JSON.parseObject(request.getInputStream(), JSONObject.class);
        Record record = new Record(jsonObject);
        logger.info("record:{}", record);
        if (record.getGid() == 0 || record.getAccount() == null) {
            logger.warn("can not get enough param!record:{}", record);
            return Response.getForbidResponse("can not get enough param!record:" + record);
        }
        //0. 登录授权记录入库
        recordService.addRecord(record);

        boolean hasAuthRecord = authService.findAuthByGidAndDevId(record.getGid(), record.getDeviceId());
        if (hasAuthRecord) {
            logger.info("hasAutoAuthRecord:{},account:{} is allow to login", true, record.getAccount());
            return Response.getAllowResponse();
        }
        //1. 检测自动授权
        if (commonConfig.isAutoAuth()) {
            long alreadyAuthSize = authService.findAutoGrantSizeByGidAndDeviceType(record.getGid(), record.getDeviceType());
            boolean autoAuth = DeviceCheckUtil.deviceCheck(record.getDeviceType(), alreadyAuthSize, commonConfig.getDeviceNums());
            if (autoAuth) {
                Auth auth = new Auth(record);
                auth.setStatus(Const.AUTH_STATUS_AUTO);
                authService.addAuth(auth);
                logger.info("device check allow account:{} to login", record.getAccount());
                return Response.getAllowResponse();
            }
            IpCheckUtil ipCheckUtil = new IpCheckUtil();
            autoAuth = ipCheckUtil.ipCheck(record.getIp(), commonConfig.getIpList());
            if (autoAuth) {
                authService.addAuth(new Auth(record));
                logger.info("ip check allow account:{} to login", record.getAccount());
                return Response.getAllowResponse();
            }
            if (commonConfig.getWhiteList() != null) {
                autoAuth = commonConfig.getWhiteList().contains(record.getAccount());
                if (autoAuth) {
                    authService.addAuth(new Auth(record));
                    logger.info("white list allow account:{} to login", record.getAccount());
                    return Response.getAllowResponse();
                }
            }
        }

        //2. 检查授权方式
        switch (commonConfig.getAuthMethod()) {
            case 0:
                //不做任何操作
                logger.info("don't do anything just forbid,account:{}", record.getAccount());
                return Response.getForbidResponse();
            //2.1 短信验证码
            case 1:
                return Response.getSmsResponse();
            //2.2 第三方验证
            case 2:
                return handlerThirdAuth(record.getAccount());
            case 3:
                //2.3 管理员审核
                // 2.3.1 入库审核表
                authService.addAuth(new Auth(record));
                return Response.getWaitAdminResponse();
            //错误
            default:
                //返回错误
                return Response.getForbidResponse("unknown auth method:" + commonConfig.getAuthMethod());
        }
    }

    private JSONObject handlerThirdAuth(String account) {
        if (commonConfig.getThirdAuthUrl() == null || "".equals(commonConfig.getThirdAuthUrl().trim())) {
            logger.warn("auth method is third but not get third url");
            return Response.getForbidResponse("auth method is third but not get third url");
        }
        if (!StaticParam.isThirdAuthUrlCheck()) {
            if (HttpUtil.checkUrl(commonConfig.getThirdAuthUrl())) {
                StaticParam.setThirdAuthUrlCheck(true);
            } else {
                logger.warn("third url un pass url：{} check", commonConfig.getThirdAuthUrl());
                return Response.getForbidResponse("third url un pass url check");
            }
        }
        Map<String, String> param = new HashMap<>(1);
        param.put("account", account);
        String result = HttpUtil.sendRequest(commonConfig.getThirdAuthUrl(), "GET", param);
        if (result == null || "".equals(result.trim())) {
            logger.warn("third url request fail,result:{}", result);
            return Response.getForbidResponse("third url request fail,result:" + result);
        }

        try {
            JSONObject resultJson = JSON.parseObject(result);
            if (resultJson.getInteger(Const.THIRD_RESPONSE_CODE) == 0) {
                logger.info("third auth response allow login");
                return Response.getAllowResponse();
            }
            logger.info("request response is:{}", result);
            return Response.getForbidResponse("request response is:" + result);
        } catch (JSONException e) {
            logger.warn("can't parse request response:{}", result);
            return Response.getForbidResponse("can't parse request response:" + result);
        }
    }

    @PostMapping("auth/grant")
    @ResponseBody
    public JSONObject grantAuth(Integer id, Integer status) {
        if (id == null || status == null) {
            return Response.getFailResponse("id or status is null");
        }
        int result = authService.updateAuthStatus(id, status);
        if (result == 0) {
            logger.info("id:{} grant auth to status:{}", id, status);
            return Response.getSuccessResponse();
        }
        logger.info("can not grant due to auth id :{} is not exist", id);
        return Response.getFailResponse("can not grant due to auth id:[" + id + "] is not exist");
    }

    @GetMapping("auth/unauthorized/list")
    @ResponseBody
    public JSONObject getUnGrant(String pageNum, String pageSize) {
        Page<Auth> page = authService.findAuthByStatusAndPage(1, pageNum, pageSize);
        return Response.getPageResponse(page);
    }

    @GetMapping("auth/list")
    @ResponseBody
    public JSONObject getAll(String pageNum, String pageSize) {
        Page<Auth> page = authService.findAllAuthByPage(pageNum, pageSize);
        return Response.getPageResponse(page);
    }
}
