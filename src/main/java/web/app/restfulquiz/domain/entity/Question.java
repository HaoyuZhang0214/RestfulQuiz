package web.app.restfulquiz.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Table(name = "Question")
public class Question implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Integer question_id;

    @Column(name = "quiz_id")
    private Integer quiz_id;

    @Column(name = "content")
    private String content;

    @Column(name = "status")
    private boolean status;

}
