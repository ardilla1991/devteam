package by.htp.devteam.dao;

import java.util.HashMap;
import java.util.Map;

import by.htp.devteam.bean.Order;
import by.htp.devteam.bean.Qualification;

public interface OrderQualificationDao {
	void add(Order order, HashMap<Qualification, Integer> qualifications);
	Map<Qualification, Integer> getByOrder(Order order);
}
