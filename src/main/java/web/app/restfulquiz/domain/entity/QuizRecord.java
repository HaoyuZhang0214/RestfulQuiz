package web.app.restfulquiz.domain.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "QuizRecord")
@EqualsAndHashCode
public class QuizRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id")
    private int record_id;

    @Column(name = "quiz_id")
    private int quiz_id;

    @Column(name = "user_id")
    private int user_id;

    @Column(name = "taken_date")
    private String taken_date;

    @Column(name = "score")
    private int score;

}
