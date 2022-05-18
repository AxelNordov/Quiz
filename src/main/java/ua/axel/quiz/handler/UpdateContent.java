package ua.axel.quiz.handler;

import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Optional;

public interface UpdateContent {
	void setContent(BotApiObject botApiObject);

	Optional<BotApiMethod<Message>> handle();
}
