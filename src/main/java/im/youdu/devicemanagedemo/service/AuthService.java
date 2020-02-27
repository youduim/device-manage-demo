package im.youdu.devicemanagedemo.service;

import im.youdu.devicemanagedemo.dao.AuthRepository;
import im.youdu.devicemanagedemo.entity.Auth;
import im.youdu.devicemanagedemo.entity.Const;
import im.youdu.devicemanagedemo.entity.ConstDevice;
import im.youdu.devicemanagedemo.util.PageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AuthService implements AuthServiceI {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);


    @Resource
    private AuthRepository authRepository;

    @Override
    public Auth addAuth(Auth auth) {

        List<Auth> list = authRepository.findAuthByGidAndDeviceIdEquals(auth.getGid(), auth.getDeviceId());
        if (list.size() > 0) {
            return list.get(0);
        }
        auth.setCreateTime(new Date());
        return authRepository.save(auth);
    }

    @Override
    public void delAuth(int id) {
        authRepository.deleteById(id);
    }

    @Override
    public Auth findAuthById(int id) {
        Optional<Auth> optionalAuth = authRepository.findById(id);
        return optionalAuth.orElse(null);
    }

    @Override
    public List<Auth> findAuthByGid(int gid) {
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        return authRepository.findAllByGidEquals(gid, sort);
    }

    @Override
    public boolean findAuthByGidAndDevId(int gid, String devId) {
        if (devId == null || "".equals(devId)) {
            return false;
        }
        List<Integer> status = new ArrayList<>(2);
        status.add(Const.AUTH_STATUS_SUCCESS);
        status.add(Const.AUTH_STATUS_AUTO);
        List<Auth> list = authRepository.findAuthByGidAndDeviceIdEqualsAndStatusIn(gid, devId, status);
        return list.size() > 0;
    }

    @Override
    public int updateAuthStatus(int id, int status) {
        Optional<Auth> optionalAuth = authRepository.findById(id);
        if (!optionalAuth.isPresent()) {
            return -1;
        }

        Auth auth = optionalAuth.get();
        auth.setStatus(status);
        auth.setUpdateTime(new Date());
        auth = authRepository.save(auth);
        if (auth != null && auth.getId() != 0) {
            return 0;
        }
        return -1;
    }

    @Override
    public long findAutoGrantSizeByGidAndDeviceType(int gid, int deviceType) {
        ConstDevice constDevice=new ConstDevice();
        return authRepository.countByGidEqualsAndStatusEqualsAndDeviceTypeIn(gid,Const.AUTH_STATUS_AUTO,constDevice.getSameDeviceType(deviceType));
    }

    @Override
    public Page<Auth> findAuthByStatusAndPage(int status, String pageNum, String pageSize) {
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        try {
            Pageable pageable = PageRequest.of(PageUtil.getPageNum(pageNum) - 1, PageUtil.getPageSize(pageSize, PageUtil.DEFAULT_PAGE_SIZE), sort);
            return authRepository.findAllByStatusEquals(status, pageable);
        } catch (NumberFormatException e) {
            logger.warn("fail to get pageNum and pageSize,error:{}", e.getMessage());
            return null;
        }
    }

    @Override
    public Page<Auth> findAllAuthByPage(String pageNum, String pageSize) {
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        try {
            Pageable pageable = PageRequest.of(PageUtil.getPageNum(pageNum) - 1, PageUtil.getPageSize(pageSize, PageUtil.DEFAULT_PAGE_SIZE), sort);
            return authRepository.findAll(pageable);
        } catch (NumberFormatException e) {
            logger.warn("fail to get pageNum and pageSize,error:{}", e.getMessage());
            return null;
        }
    }
}
