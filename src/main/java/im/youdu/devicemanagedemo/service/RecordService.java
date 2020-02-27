package im.youdu.devicemanagedemo.service;

import im.youdu.devicemanagedemo.dao.RecordRepository;
import im.youdu.devicemanagedemo.entity.Record;
import im.youdu.devicemanagedemo.util.PageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RecordService implements RecordServiceI {
    private static final Logger logger = LoggerFactory.getLogger(RecordService.class);

    @Resource
    private RecordRepository recordRepository;

    @Override
    public void addRecord(Record record) {
        if (record == null || record.getGid() == 0) {
            return;
        }
        recordRepository.save(record);
    }

    @Override
    public Page<Record> getRecordByPage(String pageNum, String pageSize) {
        Sort sort = new Sort(Sort.Direction.DESC, "recordTime");
        try {
            Pageable pageable = PageRequest.of(PageUtil.getPageNum(pageNum) - 1, PageUtil.getPageSize(pageSize, PageUtil.DEFAULT_PAGE_SIZE), sort);
            return recordRepository.findAll(pageable);
        } catch (NumberFormatException e) {
            logger.warn("fail to get pageNum and pageSize,error:{}", e.getMessage());
            return null;
        }
    }

    @Override
    public List<Record> getRecordAll() {
        return recordRepository.findAll();
    }
}
