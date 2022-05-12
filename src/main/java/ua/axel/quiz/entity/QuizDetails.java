package ua.axel.quiz.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "quiz_details")
public class QuizDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "origin_number")
	private Integer originNumber;
	@Column(name = "description", columnDefinition = "text")
	private String description;
	@Column(name = "link_1")
	private String link1;
	@Column(name = "link_2")
	private String link2;
}
