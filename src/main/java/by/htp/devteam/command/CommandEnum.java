package by.htp.devteam.command;

public enum CommandEnum {
	LOGIN("login"),  
	SHOW_FORM("show_form"),
	PERMISSION_DENIED("permission_denied"),
	
	ADMIN_LOGIN("admin_login"),
	ADMIN_SHOW_FORM("admin_show_form"),
	ADMIN_LOGOUT("admin_logout"),
	ADMIN_PERMISSION_DENIED("admin_permission_denied"),
	//ADMIM_PROJECTS_NEW_LIST("admin_projects_new_list");
	ADMIM_ORDERS_NEW_LIST("admin_orders_new_list");
	
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
