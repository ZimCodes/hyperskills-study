package platform.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import platform.model.Record;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RecordRepository extends CrudRepository<Record, String> {
    Optional<Record> findRecordById(String id);
    @Override
    @Query
    List<Record> findAll();
}