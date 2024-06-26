package game.control.robot.rovers.command;

import java.util.Arrays;
import java.util.stream.Collectors;

public class PromptCommand {

	public final static String SPLIT_KEYWORDS_ARGUMENTS = ":";
	public final static String SPLIT_REGEX = "\\s+";

	public String command;
	public String keyWords;
	public String arguments;
	public String[] keyWordsArray;
	public String[] argumentsArray;
	public String camelCasedKeyWords;
	public String underscoreCasedKeyWords;

	public PromptCommand(String command) {

		this.command = command;

		String[] commandSplit = this.command.split(PromptCommand.SPLIT_KEYWORDS_ARGUMENTS, 2);
		this.keyWords = commandSplit[0];
		this.keyWordsArray = commandSplit[0].trim().split(PromptCommand.SPLIT_REGEX);
		this.arguments = null;
		this.argumentsArray = new String[0];
		if (commandSplit.length >= 2) {
			this.arguments = commandSplit[1];
			this.argumentsArray = commandSplit[1].trim().split(PromptCommand.SPLIT_REGEX);
		}

		this.camelCasedKeyWords = PromptCommand.makeCamelCased(this.keyWordsArray);
		this.underscoreCasedKeyWords = PromptCommand.makeUnderscoreCased(this.keyWordsArray);

	}
	
	public static String makeUnderscoreCased(String[] words) {
		
		String out = Arrays.asList(words).stream().map(w -> w.toUpperCase()).collect(Collectors.joining("_"));
		return out;
		
	}

	public static String makeCamelCased(String[] words) {

		String out = Arrays.asList(words).stream()
				.map(w -> (w.length() > 0) ? (w.substring(0, 1).toUpperCase() + w.substring(1).toLowerCase()) : w)
				.collect(Collectors.joining());

		if (out.length() > 0) {
			return out.substring(0, 1).toLowerCase() + out.substring(1);
		} else {
			return out;
		}

	}

}
