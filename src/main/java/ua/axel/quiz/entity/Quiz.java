package ua.axel.quiz.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "quiz")
public class Quiz {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
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
	@NotNull(message = "Author cannot be null")
	private Author author;
	@OneToOne(mappedBy = "quiz", cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private QuizDetails quizDetails;
}
