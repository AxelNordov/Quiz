package ua.axel.quiz.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "quiz")
public class Quiz {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "question")
	@NotNull(message = "Question cannot be null")
	private String question;
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "quiz")
	private List<Answer> answers;
	@Column(name = "right_answer")
	private Byte rightAnswer;
	@Column(name = "is_one_answer")
	private Boolean isOneAnswer;
	@ManyToOne
	@JoinColumn(name = "topic_id")
	private Topic topic;
	@ManyToOne
	@JoinColumn(name = "author_id")
	private Author author;
	@OneToOne
	@JoinColumn(name = "quiz_details_id")
	private QuizDetails quizDetails;
}
