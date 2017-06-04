package by.htp.devteam.command;

import by.htp.devteam.command.command.LoginCommandAction;

public class CommandFactory {
	
	private static CommandFactory commandFactory = new CommandFactory();
	
	private CommandFactory() {
		super();
	}
	
	public static CommandFactory getInstance() {
		return commandFactory;
	}
	
	public CommandAction chooseAction(String action) throws CommandExeption{
		System.out.println(action);
		if ( action != null ) {
			System.out.println("action=" + (CommandEnum.getNameByValue(action)));
			try {
				switch (CommandEnum.getNameByValue(action)) {
					case LOGIN:
						return new LoginCommandAction();
				}
			} catch (NullPointerException e) {
				throw new CommandExeption("invalid action");
			}
		} else {
			throw new CommandExeption("invalid action");
		}
		
		return null;
	}
	
}
