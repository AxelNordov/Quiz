package ua.axel.quiz.state;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.axel.quiz.Facade;

import java.util.Optional;

public interface State {
	Optional<BotApiMethod<Message>> start(Update update);

	Optional<BotApiMethod<Message>> handle(Facade facade, Update update);
}
