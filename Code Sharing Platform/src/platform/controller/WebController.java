package platform.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import platform.model.Record;
import platform.service.RecordService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class WebController {
    private final RecordService recordService;

    @Autowired
    public WebController(RecordService recordService) {
        this.recordService = recordService;
    }

    @GetMapping(value = "/code/{id}")
    public String htmlCode(@PathVariable String id, Model model) {
        this.applyRestrictions(id);
        Optional<Record> recordOptional = this.recordService.findById(id);
        if(recordOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Record record = recordOptional.get();
        model.addAttribute("code", record.getCode());
        model.addAttribute("date", record.getDate());
        model.addAttribute("views_restrict", record.getViews());
        model.addAttribute("time_restrict", record.getTimeLeft());
        model.addAttribute("is_views_restricted", record.isViewRestrict());
        model.addAttribute("is_time_restricted", record.isTimeRestrict());
        return "code";
    }

    @GetMapping(value = "/api/code/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> jsonCode(@PathVariable String id) {
        this.applyRestrictions(id);
        Optional<Record> recordOptional = this.recordService.findById(id);
        if (recordOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Record record = recordOptional.get();
        return Map.of("code", record.getCode(), "date", record.getDate().toString(), "time",
                record.getTimeLeft(), "views", record.getViews());
    }

    @GetMapping(value = "/code/latest")
    public String latestHtmlCode(Model model) {
        model.addAttribute("records", this.recordService.findLatestTen());
        return "latest";
    }

    @GetMapping(value = "/api/code/latest", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Map<String, Object>> latestJsonCode() {
        return this.recordService.findLatestTenRestrict();
    }

    @PostMapping(value = "/api/code/new", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, String> newJsonCode(@Valid @RequestBody Record record) {
        Record newRecord = this.recordService.add(record);
        return Map.of("id", newRecord.getId());
    }

    @GetMapping(value = "/code/new")
    public String newHtmlForm() {
        return "create";
    }

    private void applyRestrictions(Record record) {
        if (record.isViewRestrict()) {
            record.updateViews();
            this.recordService.deleteByViews(record);
        }
        if (record.isTimeRestrict()) {
            if (record.getTimeLeft() <= 0) {
                this.recordService.deleteById(record.getId());
            }
        }
    }
    private void applyRestrictions(String id) {
        Optional<Record> recordOptional = this.recordService.findById(id);
        if (recordOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Record record = recordOptional.get();
        this.applyRestrictions(record);
    }
}
