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
	 * @param module from request
	 * @return CommandFactory object
	 * @throws ControllerException
	 */
	public static ControllerFactory getController(String name) throws ControllerException {
		if ( name == null )
			throw new ControllerException(MSG_COMMAND_EMPTY_MODULE);
		
		boolean found = lookup(name);
        if ( !found ) 
        	throw new ControllerException(MSG_COMMAND_INVALID_MODULE + name);
        
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
