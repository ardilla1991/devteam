package by.htp.devteam.service;

import java.util.List;

import by.htp.devteam.bean.Order;
import by.htp.devteam.bean.Work;

public interface OrderWorkService {
	void add(Order order, String[] ids);
	List<Work> getByOrder(Order order);
}
