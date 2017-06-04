package by.htp.devteam.command;

public enum CommandEnum {
	LOGIN("login");
	
	private final String value;
	
	private CommandEnum(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public static CommandEnum getNameByValue(String code) {
        for (int i = 0; i < CommandEnum.values().length; i++) {
            if (code.equals(CommandEnum.values()[i].value))
                return CommandEnum.values()[i];
        }
        return null;
    }

}
