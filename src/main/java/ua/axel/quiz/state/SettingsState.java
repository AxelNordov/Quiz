package ua.axel.quiz.state;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ua.axel.quiz.service.AuthorService;
import ua.axel.quiz.service.CategoryService;
import ua.axel.quiz.service.LocaleMessageService;
import ua.axel.quiz.service.UserService;
import ua.axel.quiz.util.SendMessageUtil;

import java.util.List;
import java.util.Optional;

@Component
public class SettingsState extends State {
	@Autowired
	private UserService userService;
	@Autowired
	private AuthorService authorService;
	@Autowired
	private CategoryService categoryService;

	@Override
	public Optional<BotApiMethod<Message>> start(String chatId) {
		var sendMessage = SendMessageUtil
				.getSendMessageWithMainMenuKeyboard(chatId,
						localeMessageService.getMessage(
								"message.you-are-in", localeMessageService.getMessage("menu.settings-button.name")),
						List.of(localeMessageService.getMessage("menu.main-button.name"),
								localeMessageService.getMessage("menu.category-button.name")));
		return Optional.of(sendMessage);
	}

	@Override
	public Optional<BotApiMethod<Message>> handle(Message message) {
		var userId = message.getFrom().getId();
		var chatId = message.getChatId().toString();
		var text = message.getText();
		if (text.equals(localeMessageService.getMessage("menu.main-button.name"))) {
			return changeState(userId, chatId, StateName.MAIN_STATE);
		} else if (text.equals(localeMessageService.getMessage("menu.category-button.name"))) {
			var sendMessage = SendMessageUtil
					.getSendMessageWithMainMenuKeyboard(chatId,
							localeMessageService.getMessage("message.choose-the-category"),
							categoryService.getAllTitles());
			return Optional.of(sendMessage);
		} else if (categoryService.getAllTitles().contains(text)) {
			userService.setCategory(userId, categoryService.findByTitle(text));
			var sendMessage = SendMessageUtil
					.getSendMessageWithMainMenuKeyboard(chatId,
							text + ": choose subcategory",
							authorService.getAllTitlesByCategory(categoryService.findByTitle(text)));
			return Optional.of(sendMessage);
		} else if (authorService.getAllTitlesByCategory(userService.findById(userId).getCategory()).contains(text)) {
			userService.setAuthor(userId, authorService.findByTitle(text));
			return start(chatId);
		}
		return Optional.empty();
	}
}
