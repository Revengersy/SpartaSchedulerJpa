package sparta.sparta_scheduler_jpa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import sparta.sparta_scheduler_jpa.entity.Schedule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Getter
public class ScheduleResponseDto {
    private Long id;
    private String task;
    private String writerName;
    private LocalDateTime writtenDate;
    private LocalDateTime editedDate;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.task = schedule.getTask();
        this.writerName = schedule.getUser().getUserName();
        this.writtenDate = schedule.getCreatedAt();
        this.editedDate = schedule.getModifiedAt();
    }
}