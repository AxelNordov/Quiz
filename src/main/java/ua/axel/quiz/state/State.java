package ua.axel.quiz.state;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Optional;

public interface State {
	Optional<BotApiMethod<Message>> start(Message message);

	Optional<BotApiMethod<Message>> handle(Message message);
}
