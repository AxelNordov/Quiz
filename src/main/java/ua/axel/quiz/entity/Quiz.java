package ua.axel.quiz.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Quiz {
	@Id
	private Long id;
	private String question;
	private int rightAnswer;
	@ManyToOne
	private Category category;
	@ManyToOne
	private Source source;
}
