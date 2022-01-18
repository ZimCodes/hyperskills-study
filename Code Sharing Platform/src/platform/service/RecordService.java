package platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import platform.model.Record;
import platform.repository.RecordRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecordService {
    private final RecordRepository repo;

    @Autowired
    public RecordService(RecordRepository repository) {
        this.repo = repository;
    }

    public Optional<Record> findById(String id) {
        return this.repo.findRecordById(id);
    }

    public List<Map<String, Object>> findLatestTenRestrict() {
        List<Record> records = this.repo.findAll();
        return records.stream()
                .filter(record -> record.getViews() == 0 && record.getTime() == 0)
                .limit(10)
                .map(RecordService::toMapRestrict)
                .collect(Collectors.toList());
    }

    public List<Map<String, Object>> findLatestTen() {
        List<Record> records = this.repo.findAll();
        return records.stream()
                .filter(record -> record.getViews() == 0 && record.getTime() == 0)
                .limit(10)
                .map(RecordService::toMap)
                .collect(Collectors.toList());
    }

    public Record add(Record record) {
        record.setId(UUID.randomUUID().toString());
        record.setDate(LocalDateTime.now());

        if (record.getViews() > 0) {
            record.setViewRestrict(true);
        }

        if (record.getTime() > 0) {
            record.setTimeRestrict(true);
        }
        return this.repo.save(record);
    }

    public void deleteByViews(Record record) {
        if (record.getViews() < 0) {
            this.repo.deleteById(record.getId());
        } else {
            this.repo.save(record);
        }
    }

    public void deleteById(String uuid) {
        this.repo.deleteById(uuid);
    }


    private static Map<String, Object> toMapRestrict(Record record) {
        return Map.of("code", record.getCode(), "date", record.getDate().toString(), "time",
                record.getTime(), "views", record.getViews());
    }

    private static Map<String, Object> toMap(Record record) {
        return Map.of("code", record.getCode(), "date", record.getDate().toString());
    }
}