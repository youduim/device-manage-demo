package im.youdu.devicemanagedemo.service;

import im.youdu.devicemanagedemo.entity.Record;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 申请表服务层
 */
public interface RecordServiceI {
    /**
     * 新增流水记录
     *
     * @param record 记录
     */
    void addRecord(Record record);

    /**
     * 获取流水记录
     *
     * @param pageNum  页码
     * @param pageSize 每页数
     * @return 结果
     */
    Page<Record> getRecordByPage(String pageNum, String pageSize);

    List<Record> getRecordAll();
}
