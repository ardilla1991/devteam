package by.htp.devteam.command;

public class CommandExeption extends Exception{
	
	private static final long serialVersionUID = 2879996083995167679L;
	private Exception _hidden;
	
	public CommandExeption(String s) {
		super(s);
	}
	
	public CommandExeption(String s, Exception e) {
		super(s);
		_hidden = e;
	}
	
	public Exception getCommandExeption() {
		return _hidden;
	}
}
