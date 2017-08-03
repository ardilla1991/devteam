package by.htp.devteam.controller;

import by.htp.devteam.module.Controller;
import by.htp.devteam.module.ControllerExeption;
import by.htp.devteam.module.ControllerFactory;

public final class Router {
	
	/**
	 * Controller for model
	 */
	private Controller moduleController;
	
	/**
	 * Action for controller
	 */
	private String action;
	
	public Router() {
		super();
	}

	public Controller getModuleController() {
		return moduleController;
	}

	public void setModuleController(Controller moduleController) {
		this.moduleController = moduleController;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
	public void getRoute(String url) throws ControllerExeption {
		moduleController = ControllerFactory.getController(action).chooseController();
	}
	
}
