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
	@Column(name = "url_1")
	private String url1;
	@Column(name = "url_2")
	private String url2;
}
