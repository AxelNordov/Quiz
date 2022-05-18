package ua.axel.quiz.entity;

import lombok.Builder;
import lombok.Getter;
import ua.axel.quiz.state.StateName;

@Getter
@Builder
public class UserState {
	private long id;
	private StateName state;
}
