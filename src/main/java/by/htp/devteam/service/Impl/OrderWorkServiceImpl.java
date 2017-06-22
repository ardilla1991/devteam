package by.htp.devteam.service.Impl;

import java.util.List;

import by.htp.devteam.bean.Order;
import by.htp.devteam.bean.Work;
import by.htp.devteam.dao.DaoFactory;
import by.htp.devteam.dao.OrderWorkDao;
import by.htp.devteam.service.OrderWorkService;

public class OrderWorkServiceImpl implements OrderWorkService{
	
	private OrderWorkDao orderWorkDao;
	
	public OrderWorkServiceImpl() {
		super();
		DaoFactory daoFactory = DaoFactory.getInstance();
		orderWorkDao = daoFactory.getOrderWorkDao();
	}
	public void add(Order order, String[] ids) {
		System.out.println("SERVICE");
		orderWorkDao.add(order, ids);
	}
	@Override
	public List<Work> getByOrder(Order order) {
		
		return orderWorkDao.getByOrder(order);
	}
}
