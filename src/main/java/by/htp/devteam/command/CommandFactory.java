package by.htp.devteam.command;

import by.htp.devteam.command.user.OrderNewListAction;
import by.htp.devteam.command.user.PermissionDeniedAction;
import by.htp.devteam.command.user.ProjectAddAction;
import by.htp.devteam.command.user.ProjectAddMessageAction;
import by.htp.devteam.command.user.ProjectFindAction;
import by.htp.devteam.command.user.ProjectListAction;
import by.htp.devteam.command.user.ProjectListByEmployeeAction;
import by.htp.devteam.command.user.ProjectShowAddFormAction;
import by.htp.devteam.command.user.ProjectUpdateHoursAction;
import by.htp.devteam.command.user.ProjectViewAction;
import by.htp.devteam.command.admin.UserListAction;
import by.htp.devteam.command.user.LoginAction;
import by.htp.devteam.command.user.LogoutAction;
import by.htp.devteam.command.user.OrderAddAction;
import by.htp.devteam.command.user.OrderAddMessageAction;
import by.htp.devteam.command.user.OrderListAction;
import by.htp.devteam.command.user.OrderShowAddFormAction;
import by.htp.devteam.command.user.OrderViewAction;
import by.htp.devteam.command.user.ShowAuthorizationFormAction;
import by.htp.devteam.command.user.UserViewAction;
import by.htp.devteam.controller.HTTPMethod;

import static by.htp.devteam.command.util.ConstantValue.*;

/**
 * Factory for creating Action object
 * ENUM of all command in system
 * @author julia
 *
 */
public enum CommandFactory {
	LOGIN { 
		public CommandAction chooseAction() { 
			return new LoginAction(); 
		} 
		public HTTPMethod getHTTPMethod() { 
			return HTTPMethod.POST; 
		} 
	},  
	LOGIN_SHOW_FORM { 
		public CommandAction chooseAction() { 
			return new ShowAuthorizationFormAction(); 
		} 
	},
	PERMISSION_DENIED { 
		public CommandAction chooseAction() { 
			return new PermissionDeniedAction(); 
		} 
	},
	ORDER_LIST { 
		public CommandAction chooseAction() { 
			return new OrderListAction(); 
		} 
	},
	ORDER_SHOW_ADD_FORM { 
		public CommandAction chooseAction() { 
			return new OrderShowAddFormAction(); 
		} 
	},
	ORDER_ADD { 
		public CommandAction chooseAction() { 
			return new OrderAddAction(); 
		} 
		public HTTPMethod getHTTPMethod() { 
			return HTTPMethod.POST; 
		}
	},
	ORDER_VIEW { 
		public CommandAction chooseAction() { 
			return new OrderViewAction(); 
		} 
	},
	ORDER_NEW_LIST { 
		public CommandAction chooseAction() { 
			return new OrderNewListAction(); 
		}
	},
	
	ORDER_ADD_MESSAGE { 
		public CommandAction chooseAction() { 
			return new OrderAddMessageAction(); 
		} 
	},
	
	PROJECT_SHOW_ADD_FORM { 
		public CommandAction chooseAction() { 
			return new ProjectShowAddFormAction(); 
		} 
	},
	PROJECT_ADD { 
		public CommandAction chooseAction() { 
			return new ProjectAddAction(); 
		} 
		public HTTPMethod getHTTPMethod() { 
			return HTTPMethod.POST; 
		}
	},
	PROJECT_LIST { 
		public CommandAction chooseAction() { 
			return new ProjectListAction(); 
		} 
	},
	PROJECT_LIST_BY_EMPLOYEE { 
		public CommandAction chooseAction() { 
			return new ProjectListByEmployeeAction(); 
		} 
	},
	PROJECT_VIEW { 
		public CommandAction chooseAction() { 
			return new ProjectViewAction(); 
		} 
	},
	PROJECT_UPDATE_HOURS { 
		public CommandAction chooseAction() { 
			return new ProjectUpdateHoursAction(); 
		} 
		public HTTPMethod getHTTPMethod() { 
			return HTTPMethod.POST; 
		}
	},
	PROJECT_ADD_MESSAGE { 
		public CommandAction chooseAction() { 
			return new ProjectAddMessageAction(); 
		} 
	},
	PROJECT_FIND {
		public CommandAction chooseAction() { 
			return new ProjectFindAction(); 
		}
	},
	USER_VIEW { 
		public CommandAction chooseAction() { 
			return new UserViewAction(); 
		} 
	},
	USER_LIST { 
		public CommandAction chooseAction() { 
			return new UserListAction(); 
		} 
	},
	LOGOUT { public CommandAction chooseAction() { 
		return new LogoutAction(); 
		} 
	};

	/**
	 * Select action for command
	 * @return Object with CommandAction type
	 */
	public abstract CommandAction chooseAction();
	
	/**
	 * Get HTTP method of http-query. Default value is GET
	 * @return
	 */
	public HTTPMethod getHTTPMethod() {
		return HTTPMethod.GET;
	};
	
	/**
	 * Get command in system according to selected action
	 * Validate if action is null. Find action in enum and throw exceptiom if action not found
	 * @param action from request
	 * @return CommandFactory object
	 * @throws CommandExeption
	 */
	public static CommandFactory getAction(String action) throws CommandExeption {
		if ( action == null )
			throw new CommandExeption(MSG_COMMAND_EMPTY_ACTION);
		
		String enumAction = action.toUpperCase();
		boolean found = lookup(action);
        if ( !found ) 
        	throw new CommandExeption(MSG_COMMAND_INVALID_ACTION + action);
        
        return CommandFactory.valueOf(enumAction);
       }
	
	/**
	 * Find action in enam
	 * @param action
	 * @return boolean
	 */
	public static boolean lookup(String action) {
		String enumAction = action.toUpperCase();
		boolean found = false;
        for ( CommandFactory enumElement: values() ){
           if ( enumElement.toString().equals(enumAction) ) 
        	   found = true;
        }  
		return found;
	}
	
}
