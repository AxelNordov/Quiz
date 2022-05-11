package ua.axel.quiz.entity;

import lombok.*;

import javax.persistence.*;

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
	private String body;
	@Column(name = "order_number")
	private Byte orderNumber;
	@ManyToOne
	@JoinColumn(name = "quiz_id")
	private Quiz quiz;
}
