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
@Table(name = "Options")
public class Option implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_id")
    private Integer option_id;

    @Column(name = "question_id")
    private Integer question_id;

    @Column(name = "content")
    private String content;

    @Column(name = "is_solution")
    private boolean is_solution;

}
