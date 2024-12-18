package sparta.sparta_scheduler_jpa.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import sparta.sparta_scheduler_jpa.dto.ScheduleResponseDto;
import sparta.sparta_scheduler_jpa.entity.Schedule;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class ScheduleRepository {


    public ScheduleResponseDto saveSchedule(Schedule schedule){
        return null;
    };

    public List<ScheduleResponseDto> findAllSchedules(){
        return null;
    };

    public Optional<Schedule> findScheduleById(Long id){
        return null;
    };

    public List<ScheduleResponseDto> findSchedulesByDate(LocalDate date){
        return null;
    };
    public List<ScheduleResponseDto> findAllByFilters(LocalDate editedDate, String writerName){
        return null;
    };

    public Schedule findScheduleByIdOrElseThrow(Long id){
        return null;
    };
    public Schedule findScheduleByWriterOrElseThrow(String writerName){
        return null;
    };


    public int updateScheduleDetails(Schedule schedule){
        return 0;
    };


    public int updateWriterName(Long id, String writerName){
        return 0;
    };

    public int deleteSchedule(Long id){
        return 0;
    };


//    private RowMapper<ScheduleResponseDto> scheduleRowMapper() {
//        return new RowMapper<ScheduleResponseDto>() {
//            @Override
//            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
//                return new ScheduleResponseDto(
//                        rs.getLong("id"),                         // id
//                        rs.getString("task"),                     // task
//                        rs.getString("writer_id"),              // writer_name
//                        rs.getObject("edited_time", ZonedDateTime.class), // edited_time
//                        rs.getObject("written_time", ZonedDateTime.class) // written_time
//                );
//            }
//        };
//    }

//    private RowMapper<Schedule> scheduleRowMapperV2() {
//        return new RowMapper<Schedule>() {
//            @Override
//            public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
//                return new Schedule(
//                        rs.getString("task"),                     // task
//                        rs.getString("writer_id"),              // writer_name
//                        rs.getString("password")                 // password
//                );
//            }
//        };
//    }

}
