package sparta.sparta_scheduler_jpa.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sparta.sparta_scheduler_jpa.Service.ScheduleService;
import sparta.sparta_scheduler_jpa.dto.ScheduleRequestDto;
import sparta.sparta_scheduler_jpa.dto.ScheduleResponseDto;

import java.util.List;


@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto requestDto) {
        ScheduleResponseDto schedule = scheduleService.saveSchedule(requestDto);
        return new ResponseEntity<>(schedule, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> findAllSchedules() {
        List<ScheduleResponseDto> schedules = scheduleService.findAllSchedules();
        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findScheduleById(@PathVariable Long id) {
        ScheduleResponseDto schedule = scheduleService.findScheduleById(id);
        return new ResponseEntity<>(schedule, HttpStatus.OK);
    }

    @GetMapping("/writer/{writerName}")
    public ResponseEntity<List<ScheduleResponseDto>> findSchedulesByWriter(@PathVariable String writerName) {
        List<ScheduleResponseDto> schedules = scheduleService.findSchedulesByWriter(writerName);
        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<ScheduleResponseDto>> findScheduleByDate(@PathVariable String date) {
        List<ScheduleResponseDto> schedules = scheduleService.findScheduleByDate(date);
        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }

    @GetMapping("/filtered")
    public ResponseEntity<List<ScheduleResponseDto>> findAllSchedulesFiltered(
            @RequestParam(required = false) String editedDate,
            @RequestParam(required = false) String writerName) {
        List<ScheduleResponseDto> schedules = scheduleService.findAllSchedulesFiltered(editedDate, writerName);
        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
            @PathVariable Long id,
            @RequestBody ScheduleRequestDto requestDto) {
        ScheduleResponseDto updatedSchedule = scheduleService.updateSchedule(
                id, requestDto.getTask(), requestDto.getWriterName(), requestDto.getPassword());
        return new ResponseEntity<>(updatedSchedule, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateWriterName(
            @PathVariable Long id,
            @RequestBody ScheduleRequestDto requestDto) {
        ScheduleResponseDto updatedSchedule = scheduleService.updateWriterName(id, requestDto.getWriterName());
        return new ResponseEntity<>(updatedSchedule, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable Long id,
            @RequestBody ScheduleRequestDto requestDto) {
        scheduleService.deleteSchedule(id, requestDto.getPassword());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}