package sparta.sparta_scheduler_jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "comment")
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String comment;

    @ManyToOne
    @JoinColumn(nullable = false, name = "schedule_id")
    private Schedule schedule;

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    public Comment() {} // 필수

    public Comment(String comment, Schedule schedule, User user) {
        this.comment = comment;
        this.schedule = schedule;
        this.user = user;
    }
}