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
import by.htp.devteam.controller.ActionEnum;

public enum CommandEnum {
	LOGIN { 
		public CommandAction chooseAction() { 
			return new LoginAction(); 
		} 
		public ActionEnum getHTTPMethod() { 
			return ActionEnum.POST; 
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
		public ActionEnum getHTTPMethod() { 
			return ActionEnum.POST; 
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
		public ActionEnum getHTTPMethod() { 
			return ActionEnum.POST; 
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
		public ActionEnum getHTTPMethod() { 
			return ActionEnum.POST; 
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

	public abstract CommandAction chooseAction();
	public ActionEnum getHTTPMethod() {
		return ActionEnum.GET;
	};
	
	public static CommandEnum getAction(String action) throws CommandExeption {
		if ( action == null )
			throw new CommandExeption("Action is empty ");
		
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
