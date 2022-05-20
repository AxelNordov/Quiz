package ua.axel.quiz.entity;

import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "users")
public class User {
	@Id
	@Column(name = "id")
	private Long id;
	@Column(name = "state_name")
	private String stateName;
	@Setter
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	@Setter
	@ManyToOne
	@JoinColumn(name = "author_id")
	private Author author;
}
