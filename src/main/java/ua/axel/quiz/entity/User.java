package ua.axel.quiz.entity;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class User {
	long id;
	Map<String, String> settings;
}
