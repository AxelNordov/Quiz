package ua.axel.quiz.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Set;

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
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id")
	@NotNull(message = "Category cannot be null")
	private Category category;
	@ManyToMany(mappedBy = "authors")
	private Set<User> users;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Author author = (Author) o;
		return id.equals(author.id) && Objects.equals(title, author.title) && Objects.equals(url, author.url) && category.equals(author.category);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, title, url, category);
	}
}
