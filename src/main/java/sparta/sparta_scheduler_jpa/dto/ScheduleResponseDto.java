package sparta.sparta_scheduler_jpa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import sparta.sparta_scheduler_jpa.entity.Schedule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Getter
@AllArgsConstructor
public class ScheduleResponseDto {
    private Long id;  // 고유 식별자
    private String task;  // 할 일
    private String writerName;  // 작성자명
    private LocalDateTime writtenDate;  // 작성일
    private LocalDateTime editedDate;

    // Memo class를 인자로 가지는 생성자
    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.task = schedule.getTask();
        // Make sure User entity has a method getName()
        this.writerName = schedule.getUser().getUserName();
        this.writtenDate = schedule.getCreatedAt();
        this.editedDate = schedule.getModifiedAt();
    }
}