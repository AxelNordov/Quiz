package ua.axel.quiz.state;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.axel.quiz.Facade;

import java.io.Serializable;
import java.util.Optional;

public abstract class State {

	Facade facade;

	public State(Facade facade) {
		this.facade = facade;
	}

	public abstract Optional<BotApiMethod<? extends Serializable>> handle(Update update);
}
