package im.youdu.devicemanagedemo.service;

import im.youdu.devicemanagedemo.entity.Auth;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 鉴权表服务层
 */
public interface AuthServiceI {
    /**
     * 添加授权
     *
     * @param auth 授权
     * @return 结果
     */
    Auth addAuth(Auth auth);

    /**
     * 删除授权
     *
     * @param id 授权id
     */
    void delAuth(int id);

    /**
     * 通过id查找授权
     *
     * @param id 授权id
     * @return 授权信息
     */
    Auth findAuthById(int id);

    /**
     * 通过gid查找授权
     *
     * @param gid 授权gid
     * @return 授权信息
     */
    List<Auth> findAuthByGid(int gid);

    /**
     * 通过gid和设备id确定登录授权的唯一性
     * 后期考虑改为使用mac确定
     *
     * @param gid   gid
     * @param devId 设备id
     * @return 结果
     */
    boolean findAuthByGidAndDevId(int gid, String devId);

    /**
     * 更新授权状态
     *
     * @param id     授权id
     * @param status 授权状态
     * @return 结果
     */
    int updateAuthStatus(int id, int status);

    /**
     * 通过gid和设备类型查询自动授权数
     *
     * @param gid        gid
     * @param deviceType 设备类型
     * @return 结果
     */
    long findAutoGrantSizeByGidAndDeviceType(int gid, int deviceType);

    /**
     * 通过授权状态分页查询
     *
     * @param status   授权状态
     * @param pageNum  页码
     * @param pageSize 每页数
     * @return 结果
     */
    Page<Auth> findAuthByStatusAndPage(int status, String pageNum, String pageSize);

    /**
     * 授权分页查询
     *
     * @param pageNum  页码
     * @param pageSize 每页数
     * @return 结果
     */
    Page<Auth> findAllAuthByPage(String pageNum, String pageSize);
}
