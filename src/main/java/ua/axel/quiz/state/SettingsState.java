package ua.axel.quiz.state;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.axel.quiz.Facade;
import ua.axel.quiz.service.*;

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
	public Optional<BotApiMethod<Message>> start(Update update) {
		var chatId = update.getMessage().getChatId().toString();
		var sendMessage = sendMessageService.getSendMessage(chatId,
				String.format(localeMessageService.getMessage(
						"message.you-are-in", localeMessageService.getMessage("menu.settings-button.name"))));
		sendMessage.enableMarkdown(true);
		sendMessage.setReplyMarkup(keyboardService.getMainMenuKeyboard(
				localeMessageService.getMessage("menu.main-button.name"),
				localeMessageService.getMessage("menu.category-button.name")));
		return Optional.of(sendMessage);
	}

	@Override
	public Optional<BotApiMethod<Message>> handle(Facade facade, Update update) {
		if (update.hasMessage() && update.getMessage().hasText()) {
			var message = update.getMessage();
			long userId = message.getFrom().getId();
			var text = message.getText();
			var chatId = message.getChatId().toString();
			if (text.equals(localeMessageService.getMessage("menu.main-button.name"))) {
				userStateService.setUserStateName(userId, States.MAIN_STATE);
				return facade.getState(States.MAIN_STATE).start(update);
			} else if (text.equals(localeMessageService.getMessage("menu.category-button.name"))) {
				var sendMessage = sendMessageService.getSendMessage(
						chatId, localeMessageService.getMessage("message.choose-the-category"));
				sendMessage.setReplyMarkup(keyboardService.getMainMenuKeyboard(
						categoryService.getAllCategoriesTitles().toArray(String[]::new)));
				return Optional.of(sendMessage);
			} else if (categoryService.getAllCategoriesTitles().contains(text)) {
				userService.setCategory(userId, categoryService.findByTitle(text));
				var sendMessage = sendMessageService.getSendMessage(
						chatId, text + ": choose subcategory");
				sendMessage.setReplyMarkup(keyboardService.getMainMenuKeyboard(
						authorService.getAllAuthorsTitlesByCategory(
								categoryService.findByTitle(text)).toArray(String[]::new)));
				return Optional.of(sendMessage);
			} else if (authorService.getAllAuthorsTitlesByCategory(
					userService.findById(userId).getCategory()).contains(text)) {
				userService.setAuthor(userId, authorService.findByTitle(text));
				return start(update);
			}
		}
		return Optional.empty();
	}
}
