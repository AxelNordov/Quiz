package ua.axel.quiz.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "quiz_details")
public class QuizDetails {
	@Id
	@Column(name = "quiz_id")
	private Long id;
	@OneToOne
	@MapsId
	@JoinColumn(name = "quiz_id")
	private Quiz quiz;
	@Column(name = "origin_number")
	private Integer originNumber;
	@Column(name = "description", columnDefinition = "text")
	private String description;
	@Column(name = "url_1")
	private String url1;
	@Column(name = "url_2")
	private String url2;
}
