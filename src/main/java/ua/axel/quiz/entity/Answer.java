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
public class Answer {
	@Id
	private Long id;
	private String value;
	@ManyToOne
	private Quiz quiz;
}
