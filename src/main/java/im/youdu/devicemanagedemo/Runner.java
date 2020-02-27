package im.youdu.devicemanagedemo;

import im.youdu.devicemanagedemo.entity.CommonConfig;
import im.youdu.devicemanagedemo.entity.StaticParam;
import im.youdu.devicemanagedemo.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class Runner implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(Runner.class);

    @Resource
    private CommonConfig commonConfig;

    @Override
    public void run(String... args) {
        logger.info("server start success,start initialize system.");

        logger.info("common config:{}", commonConfig);

        if (HttpUtil.checkUrl(commonConfig.getThirdAuthUrl())) {
            StaticParam.setThirdAuthUrlCheck(true);
        }
        logger.info("THIRD_AUTH_URL_CHECK:{}", StaticParam.isThirdAuthUrlCheck());
    }
}