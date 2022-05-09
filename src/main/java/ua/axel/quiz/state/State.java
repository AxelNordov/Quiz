package ua.axel.quiz.state;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.axel.quiz.Facade;

import java.io.Serializable;
import java.util.Optional;

public interface State {

	Optional<BotApiMethod<? extends Serializable>> start(Update update);

	Optional<BotApiMethod<? extends Serializable>> handle(Facade facade, Update update);

	Optional<BotApiMethod<? extends Serializable>> finish();

}
