package ua.axel.quiz.entity;

import lombok.*;
import ua.axel.quiz.state.States;

import javax.persistence.*;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
	@Id
	@Column(name = "id")
	private Long id;
	@Enumerated(EnumType.STRING)
	@Column(name = "state_name")
	private States.Name stateName;
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "user_author",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "author_id"))
	private Set<Author> authors;
}
