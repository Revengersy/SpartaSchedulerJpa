package sparta.sparta_scheduler_jpa.Service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import sparta.sparta_scheduler_jpa.dto.ScheduleRequestDto;
import sparta.sparta_scheduler_jpa.dto.ScheduleResponseDto;
import sparta.sparta_scheduler_jpa.entity.Schedule;
import sparta.sparta_scheduler_jpa.entity.User;
import sparta.sparta_scheduler_jpa.repository.ScheduleRepository;
import sparta.sparta_scheduler_jpa.repository.UserRepository;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

    public ScheduleResponseDto saveSchedule(ScheduleRequestDto requestDto) {
        log.info("Save schedule - username searching: {}", requestDto.getUsername());
        User user = userRepository.findByUserName(requestDto.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        log.info("user name for saving: {}", user.getUserName());

        Schedule schedule = new Schedule(requestDto.getTask(), user, requestDto.getPassword());
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new ScheduleResponseDto(savedSchedule);
    }

    public List<ScheduleResponseDto> findAllSchedules() {
        return scheduleRepository.findAll().stream()
                .map(ScheduleResponseDto::new)
                .collect(Collectors.toList());
    }

    public ScheduleResponseDto findScheduleById(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found"));
        return new ScheduleResponseDto(schedule);
    }

    public List<ScheduleResponseDto> findSchedulesByWriter(String writerName) {
        List<Schedule> schedules = scheduleRepository.findByUser_UserName(writerName);
        if (schedules.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No schedules found for writer");
        }
        return schedules.stream()
                .map(ScheduleResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<ScheduleResponseDto> findScheduleByDate(String date) {
        try {
            LocalDate localDate = LocalDate.parse(date);
            LocalDateTime startDateTime = localDate.atStartOfDay();
            LocalDateTime endDateTime = localDate.plusDays(1).atStartOfDay();

            // `LocalDateTime`에 맞춰 쿼리 수행
            return scheduleRepository.findByCreatedAtBetween(startDateTime, endDateTime).stream()
                    .map(ScheduleResponseDto::new)
                    .collect(Collectors.toList());
        } catch (DateTimeParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date format.");
        }
    }

    public List<ScheduleResponseDto> findAllSchedulesFiltered(String editedDate, String writerName) {
        LocalDate dateFilter = (editedDate != null && !editedDate.isEmpty()) ? LocalDate.parse(editedDate) : null;

        if (dateFilter != null) {
            return scheduleRepository.findByCreatedAtAndUser_UserName(dateFilter, writerName).stream()
                    .map(ScheduleResponseDto::new)
                    .collect(Collectors.toList());
        } else {
            return scheduleRepository.findByUser_UserName(writerName).stream()
                    .map(ScheduleResponseDto::new)
                    .collect(Collectors.toList());
        }
    }

    @Transactional
    public ScheduleResponseDto updateSchedule(Long id, String task, String userName, String password) {
        Schedule existingSchedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found"));
        if (!existingSchedule.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect password.");
        }

        existingSchedule.setTask(task);
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        existingSchedule.setUser(user);

        scheduleRepository.save(existingSchedule);
        return new ScheduleResponseDto(existingSchedule);
    }

    @Transactional
    public ScheduleResponseDto updateWriterName(Long id, String writerName) {
        if (writerName == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The writerName is a required value.");
        }

        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found"));

        schedule.getUser().setUserName(writerName);
        scheduleRepository.save(schedule);

        return new ScheduleResponseDto(schedule);
    }

    @Transactional
    public void deleteSchedule(Long id, String password) {
        Schedule existingSchedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found"));
        if (!existingSchedule.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect password.");
        }

        scheduleRepository.deleteById(id);
    }
}