package ua.axel.quiz.state;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.axel.quiz.Facade;

import java.io.Serializable;
import java.util.Optional;

@Component
public class GameState implements State {

	@Override
	public Optional<BotApiMethod<? extends Serializable>> handle(Facade facade, Update update) {
		return Optional.empty();
	}
}
