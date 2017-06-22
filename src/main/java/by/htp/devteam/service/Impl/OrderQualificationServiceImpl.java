package by.htp.devteam.service.Impl;

import java.util.HashMap;
import java.util.Map;

import by.htp.devteam.bean.Order;
import by.htp.devteam.bean.Qualification;
import by.htp.devteam.dao.DaoFactory;
import by.htp.devteam.dao.OrderQualificationDao;
import by.htp.devteam.dao.OrderWorkDao;
import by.htp.devteam.service.OrderQualificationService;

public class OrderQualificationServiceImpl implements OrderQualificationService{

	private OrderQualificationDao orderQualificationDao;
	
	public OrderQualificationServiceImpl() {
		super();
		DaoFactory daoFactory = DaoFactory.getInstance();
		orderQualificationDao = daoFactory.getOrderQualificationDao();
	}
	
	public void add(Order order, String[] qualificationsIds) {
		
		HashMap<Qualification, Integer> qualifications = new HashMap<Qualification, Integer>();
		for ( String qualificationId : qualificationsIds ) {
			Qualification qualification = new Qualification();
			qualification.setId(Long.valueOf(qualificationId));
			
			qualifications.put(qualification, 1);
		}
		orderQualificationDao.add(order, qualifications);
		
	}

	@Override
	public Map<Qualification, Integer> getByOrder(Order order) {
		// TODO Auto-generated method stub
		return orderQualificationDao.getByOrder(order);
	}
}
