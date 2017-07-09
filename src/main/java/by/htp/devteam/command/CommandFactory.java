package by.htp.devteam.command;

public class CommandFactory {

	private static CommandFactory commandFactory = new CommandFactory();

	private CommandFactory() {
		super();
	}

	public static CommandFactory getInstance() {
		return commandFactory;
	}

	public CommandAction chooseAction(String action) throws CommandExeption {
		try {
			return CommandEnum.getAction(action).chooseAction();
		} catch (CommandExeption e) {
			throw new CommandExeption("invalid action", e);
		}
	}

}
