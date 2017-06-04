package by.htp.devteam.command;

public class CommandExeption extends Exception{
	
	private Exception _hidden;
	
	public CommandExeption(String s) {
		super(s);
	}
	
	CommandExeption(String s, Exception e) {
		super(s);
		_hidden = e;
	}
	
	public Exception getCommandExeption() {
		return _hidden;
	}
}
