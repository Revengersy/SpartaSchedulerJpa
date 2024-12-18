package sparta.sparta_scheduler_jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sparta.sparta_scheduler_jpa.entity.Schedule;
import sparta.sparta_scheduler_jpa.entity.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByUser_UserName(String writerName);

    List<Schedule> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<Schedule> findByCreatedAtAndUser_UserName(LocalDate createdAt, String writerName);
}