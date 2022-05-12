package ua.axel.quiz.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "answer")
public class Answer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
