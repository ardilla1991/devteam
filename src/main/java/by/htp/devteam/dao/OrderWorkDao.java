package by.htp.devteam.dao;

import java.util.List;

import by.htp.devteam.bean.Order;
import by.htp.devteam.bean.Work;

public interface OrderWorkDao {
	void add(Order order, String[] ids);
	List<Work> getByOrder(Order order);
}
