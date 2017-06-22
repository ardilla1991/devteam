package by.htp.devteam.command;

import by.htp.devteam.command.admin.AdminProjectNewListAction;
import by.htp.devteam.command.admin.AdminShowAuthorizationFormAction;
import by.htp.devteam.command.admin.AdminLoginAction;
import by.htp.devteam.command.admin.AdminLogoutAction;
import by.htp.devteam.command.admin.AdminOrderNewListAction;
import by.htp.devteam.command.admin.AdminPermissionDeniedAction;

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
					case SHOW_FORM:
						return new ShowAuthorizationFormAction();
					case LOGIN:
						return new LoginAction();
					case ORDER_LIST:
						return new OrderListAction();
					case ORDER_SHOW_ADD_FORM:
						return new OrderShowAddFormAction();
					case ORDER_ADD:
						return new OrderAddAction();
					case ORDER_VIEW:
						return new OrderViewAction();
					case LOGOUT:
						return new LogoutAction();
						
					case ADMIN_LOGIN:
						return new AdminLoginAction();
					case ADMIM_ORDERS_NEW_LIST:
						return new AdminOrderNewListAction();
					case ADMIN_SHOW_FORM:
						return new AdminShowAuthorizationFormAction();
					case ADMIN_LOGOUT:
						return new AdminLogoutAction();
					case ADMIN_PERMISSION_DENIED:
						return new AdminPermissionDeniedAction();
					default:
						System.out.println("not fund");
					
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
