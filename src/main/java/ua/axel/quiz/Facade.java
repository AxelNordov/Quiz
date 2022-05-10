package ua.axel.quiz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.axel.quiz.entity.Category;
import ua.axel.quiz.service.CategoryService;
import ua.axel.quiz.service.UserService;
import ua.axel.quiz.service.UserStateService;
import ua.axel.quiz.state.State;
import ua.axel.quiz.state.States;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class Facade {
	@Autowired
	private States states;
	@Autowired
	private UserStateService userStateService;
	@Autowired
	private UserService userService;
	@Autowired
	private CategoryService categoryService;


	public Optional<BotApiMethod<Message>> handle(Update update) {
		var userId = update.getMessage().getFrom().getId();
		var currUserState = states.getState(userStateService.findById(userId).getState());
		return currUserState.handle(this, update);
	}

	public void setUserCategory(Long userId, String category) {
		setUserCategory(userId, categoryService.findByName(category));
	}

	public void setUserCategory(Long userId, Category category) {
		userService.saveCategory(userId, category);
	}

	public void setUserState(Long userId, String state) {
		userService.saveState(userId, state);
		userStateService.save(userId, state);
	}

	public State getState(String state) {
		return states.getState(state);
	}

	public List<String> getAllCategoriesNames() {
		return categoryService.findAll().stream().map(Category::getName).collect(Collectors.toList());
	}
}
