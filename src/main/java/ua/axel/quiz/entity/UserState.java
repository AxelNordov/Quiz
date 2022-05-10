package ua.axel.quiz.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserState {
	private long id;
	private String state;
}
