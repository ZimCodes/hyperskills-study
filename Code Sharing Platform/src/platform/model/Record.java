package platform.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
@NoArgsConstructor
@Entity
@NamedQuery(name = "Record.findAll", query = "SELECT r FROM Record r ORDER BY r.date DESC")
public class Record {
    @Id
    @Column(name = "id")
    private String id;
    @NotNull
    @Column(name = "code")
    private String code;
    private Integer time;
    private Integer views;
    @Column(name = "date")
    private LocalDateTime date;
    private boolean isViewRestrict;
    private boolean isTimeRestrict;

    public long getTimeLeft() {
        if (isTimeRestrict) {
            LocalDateTime expiredTime = this.date.plusSeconds(time);
            LocalDateTime currentTime = LocalDateTime.now();
            return currentTime.until(expiredTime, ChronoUnit.SECONDS);
        }
        return this.time;
    }

    public void updateViews() {
        this.views -= 1;
    }

}