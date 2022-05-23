package ua.axel.quiz.handler;

import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

public interface UpdateContent {
	void setContent(BotApiObject botApiObject);

	List<BotApiMethod<Message>> handle();
}
