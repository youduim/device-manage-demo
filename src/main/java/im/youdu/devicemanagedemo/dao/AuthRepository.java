package im.youdu.devicemanagedemo.dao;

import im.youdu.devicemanagedemo.entity.Auth;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthRepository extends JpaRepository<Auth, Integer> {
    /**
     * 通过gid查找授权信息
     *
     * @param gid  用户gid
     * @param sort 排序
     * @return 结果
     */
    List<Auth> findAllByGidEquals(int gid, Sort sort);

    /**
     * 通过gid查找授权信息
     *
     * @param status   状态 值
     * @param pageable 分页
     * @return 结果
     */
    Page<Auth> findAllByStatusEquals(int status, Pageable pageable);

    /**
     * 通过gid和设备id查询
     *
     * @param gid    gid
     * @param devId  设备Id
     * @param status 状态值
     * @return 结果
     */
    List<Auth> findAuthByGidAndDeviceIdEqualsAndStatusIn(int gid, String devId, List<Integer> status);

    /**
     * 通过gid和设备id查询
     *
     * @param gid   gid
     * @param devId 设备Id
     * @return 结果
     */
    List<Auth> findAuthByGidAndDeviceIdEquals(int gid, String devId);

    Long countByGidEqualsAndStatusEqualsAndDeviceTypeIn(int gid, int status, List<Integer> deviceTypes);
}
