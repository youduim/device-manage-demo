package im.youdu.devicemanagedemo.dao;

import im.youdu.devicemanagedemo.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Record, Integer> {
}
