package ua.axel.quiz.state;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ua.axel.quiz.service.*;

import java.util.List;
import java.util.Optional;

@Component
public class SettingsState implements State {
	@Autowired
	private UserStateService userStateService;
	@Autowired
	private UserService userService;
	@Autowired
	private AuthorService authorService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private SendMessageService sendMessageService;
	@Autowired
	private KeyboardService keyboardService;
	@Autowired
	private LocaleMessageService localeMessageService;

	@Override
	public Optional<BotApiMethod<Message>> start(Message message) {
		var chatId = message.getChatId().toString();
		var sendMessage = sendMessageService.getSendMessage(chatId,
				String.format(localeMessageService.getMessage(
						"message.you-are-in", localeMessageService.getMessage("menu.settings-button.name"))));
		sendMessage.enableMarkdown(true);
		sendMessage.setReplyMarkup(keyboardService.getMainMenuKeyboard(List.of(
				localeMessageService.getMessage("menu.main-button.name"),
				localeMessageService.getMessage("menu.category-button.name"))));
		return Optional.of(sendMessage);
	}

	@Override
	public Optional<BotApiMethod<Message>> handle(Message message) {
		long userId = message.getFrom().getId();
		var text = message.getText();
		var chatId = message.getChatId().toString();
		if (text.equals(localeMessageService.getMessage("menu.main-button.name"))) {
			userStateService.setUserState(userId, StateName.MAIN_STATE);
			return States.getState(StateName.MAIN_STATE).start(message);
		} else if (text.equals(localeMessageService.getMessage("menu.category-button.name"))) {
			var sendMessage = sendMessageService.getSendMessage(
					chatId, localeMessageService.getMessage("message.choose-the-category"));
			sendMessage.setReplyMarkup(keyboardService.getMainMenuKeyboard(
					categoryService.getAllCategoriesTitles()));
			return Optional.of(sendMessage);
		} else if (categoryService.getAllCategoriesTitles().contains(text)) {
			userService.setCategory(userId, categoryService.findByTitle(text));
			var sendMessage = sendMessageService.getSendMessage(
					chatId, text + ": choose subcategory");
			sendMessage.setReplyMarkup(keyboardService.getMainMenuKeyboard(
					authorService.getAllAuthorsTitlesByCategory(categoryService.findByTitle(text))));
			return Optional.of(sendMessage);
		} else if (authorService.getAllAuthorsTitlesByCategory(
				userService.findById(userId).getCategory()).contains(text)) {
			userService.setAuthor(userId, authorService.findByTitle(text));
			return start(message);
		}
		return Optional.empty();
	}
}
