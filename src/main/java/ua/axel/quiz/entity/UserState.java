package ua.axel.quiz.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserState {
	long id;
	String state;
}
