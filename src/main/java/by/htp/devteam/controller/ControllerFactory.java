package by.htp.devteam.controller;

import static by.htp.devteam.controller.util.ConstantValue.*;

import by.htp.devteam.controller.module.impl.EmployeeControllerImpl;
import by.htp.devteam.controller.module.impl.OrderControllerImpl;
import by.htp.devteam.controller.module.impl.ProjectControllerImpl;
import by.htp.devteam.controller.module.impl.UserControllerImpl;

/**
 * Factory for creating Module object
 * ENUM of all modules in system
 * @author julia
 *
 */
public enum ControllerFactory {
	
	USER {
		@Override
		public Controller chooseController() {
			return new UserControllerImpl();
		}
	},
	EMPLOYEE {
		@Override
		public Controller chooseController() {
			return new EmployeeControllerImpl();
		}
	},
	ORDER {
		@Override
		public Controller chooseController() {
			return new OrderControllerImpl();
		}
	},
	PROJECT {
		@Override
		public Controller chooseController() {
			return new ProjectControllerImpl();
		}
	};

	/**
	 * Select module for command
	 * @return Object with CommandAction type
	 */
	public abstract Controller chooseController();
	
	
	/**
	 * Get module controller in system according to selected module
	 * Validate if module is null. Find module in enum and throw exceptiom if module not found
	 * @param module string from request
	 * @return CommandFactory object
	 * @throws ControllerException If module parameter is empty or don't exist  
	 */
	public static ControllerFactory getController(String module) throws ControllerException {
		if ( module == null )
			throw new ControllerException(MSG_COMMAND_EMPTY_MODULE);
		
		boolean found = lookup(module);
        if ( !found ) 
        	throw new ControllerException(MSG_COMMAND_INVALID_MODULE + module);
        
        return ControllerFactory.valueOf(module.toUpperCase());
       }
	
	/**
	 * Find action in enam
	 * @param module String for module
	 * @return boolean True - if name was found, or false - if name is null or don't isset
	 */
	public static boolean lookup(String module) {
		boolean found = false;
		if ( module == null )
			return found;
		
		String enumAction = module.toUpperCase();
        for ( ControllerFactory enumElement: values() ){
           if ( enumElement.toString().equals(enumAction) ) 
        	   found = true;
        }  
		return found;
	}
	
}
