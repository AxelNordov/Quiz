package ua.axel.quiz.entity;

import lombok.Builder;
import lombok.Getter;
import ua.axel.quiz.state.States;

@Getter
@Builder
public class UserState {
	private long id;
	private States.Name state;
}
