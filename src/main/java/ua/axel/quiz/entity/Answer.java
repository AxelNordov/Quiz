package ua.axel.quiz.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "answer")
public class Answer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "body")
	@NotNull(message = "Body cannot be null")
	private String body;
	@Column(name = "order_number")
	private Byte orderNumber;
	@ManyToOne
	@JoinColumn(name = "quiz_id")
	@NotNull(message = "Quiz cannot be null")
	private Quiz quiz;
}
