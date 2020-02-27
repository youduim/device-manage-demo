package im.youdu.devicemanagedemo;

import im.youdu.devicemanagedemo.entity.CommonConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;


@RunWith(SpringRunner.class)
@SpringBootTest
public class DeviceManageDemoApplicationTests {

    @Resource
    CommonConfig commonConfig;

    @Test
    public void contextLoads() {
        System.out.println(commonConfig);
    }


}
