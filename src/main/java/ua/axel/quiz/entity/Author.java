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
@Table(name = "author")
public class Author {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
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
