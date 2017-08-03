package by.htp.devteam.module;

import static by.htp.devteam.module.util.ConstantValue.*;

import by.htp.devteam.module.controller.EmployeeController;
import by.htp.devteam.module.controller.OrderController;
import by.htp.devteam.module.controller.ProjectController;
import by.htp.devteam.module.controller.UserController;

/**
 * Factory for creating Action object
 * ENUM of all modules in system
 * @author julia
 *
 */
public enum ControllerFactory {
	
	USER {
		@Override
		public Controller chooseController() {
			return new UserController();
		}
	},
	EMPLOYEE {
		@Override
		public Controller chooseController() {
			return new EmployeeController();
		}
	},
	ORDER {
		@Override
		public Controller chooseController() {
			return new OrderController();
		}
	},
	PROJECT {
		@Override
		public Controller chooseController() {
			return new ProjectController();
		}
	};

	/**
	 * Select action for command
	 * @return Object with CommandAction type
	 */
	public abstract Controller chooseController();
	
	
	/**
	 * Get command in system according to selected action
	 * Validate if action is null. Find action in enum and throw exceptiom if action not found
	 * @param action from request
	 * @return CommandFactory object
	 * @throws ControllerExeption
	 */
	public static ControllerFactory getController(String name) throws ControllerExeption {
		if ( name == null )
			throw new ControllerExeption(MSG_COMMAND_EMPTY_MODULE);
		
		boolean found = lookup(name);
        if ( !found ) 
        	throw new ControllerExeption(MSG_COMMAND_INVALID_MODULE + name);
        
        return ControllerFactory.valueOf(name.toUpperCase());
       }
	
	/**
	 * Find action in enam
	 * @param name
	 * @return boolean True - if name was found, or false - if name is null or don't isset
	 */
	public static boolean lookup(String name) {
		boolean found = false;
		if ( name == null )
			return found;
		
		String enumAction = name.toUpperCase();
        for ( ControllerFactory enumElement: values() ){
           if ( enumElement.toString().equals(enumAction) ) 
        	   found = true;
        }  
		return found;
	}
	
}
