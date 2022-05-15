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
@Table(name = "author")
public class Author {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "title")
	@NotNull(message = "Title cannot be null")
	private String title;
	@Column(name = "url")
	private String url;
	@ManyToOne
	@JoinColumn(name = "category_id")
	@NotNull(message = "Category cannot be null")
	private Category category;
}
