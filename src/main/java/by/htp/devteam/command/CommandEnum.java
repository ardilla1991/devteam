package by.htp.devteam.command;

import by.htp.devteam.command.user.OrderNewListAction;
import by.htp.devteam.command.user.PermissionDeniedAction;
import by.htp.devteam.command.user.ProjectShowAddFormAction;
import by.htp.devteam.command.user.LoginAction;
import by.htp.devteam.command.user.LogoutAction;
import by.htp.devteam.command.user.OrderAddAction;
import by.htp.devteam.command.user.OrderListAction;
import by.htp.devteam.command.user.OrderShowAddFormAction;
import by.htp.devteam.command.user.OrderViewAction;
import by.htp.devteam.command.user.ShowAuthorizationFormAction;

public enum CommandEnum {
	LOGIN { public CommandAction chooseAction() { return new LoginAction(); } },  
	SHOW_FORM { public CommandAction chooseAction() { return new ShowAuthorizationFormAction(); } },
	PERMISSION_DENIED { public CommandAction chooseAction() { return new PermissionDeniedAction(); } },
	ORDER_LIST { public CommandAction chooseAction() { return new OrderListAction(); } },
	ORDER_SHOW_ADD_FORM { public CommandAction chooseAction() { return new OrderShowAddFormAction(); } },
	ORDER_ADD { public CommandAction chooseAction() { return new OrderAddAction(); } },
	ORDER_VIEW { public CommandAction chooseAction() { return new OrderViewAction(); } },
	ORDER_NEW_LIST { public CommandAction chooseAction() { return new OrderNewListAction(); } },
	PROJECT_SHOW_ADD_FORM { public CommandAction chooseAction() { return new ProjectShowAddFormAction(); } },
	LOGOUT { public CommandAction chooseAction() { return new LogoutAction(); } }
	;
	//ADMIM_PROJECTS_NEW_LIST("admin_projects_new_list");
	
	
	/*private final String value;
	
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
    }*/
	public abstract CommandAction chooseAction();
	
	public static CommandEnum getAction(String action) throws CommandExeption {
		String enumAction = action.toUpperCase();
		boolean found = lookup(action);
        if ( !found ) 
        	throw new CommandExeption("Invalid action: " +action);
        
        return CommandEnum.valueOf(enumAction);
       }
	
	public static boolean lookup(String action) {
		String enumAction = action.toUpperCase();
		boolean found = false;
        for ( CommandEnum enumElement: values() ){
           if ( enumElement.toString().equals(enumAction) ) 
        	   found = true;
        }  
		return found;
	}
	
}
