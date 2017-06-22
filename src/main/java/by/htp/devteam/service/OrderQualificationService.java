package by.htp.devteam.service;

import java.util.List;
import java.util.Map;

import by.htp.devteam.bean.Order;
import by.htp.devteam.bean.Qualification;
import by.htp.devteam.bean.Work;

public interface OrderQualificationService {
	void add(Order order, String[] qualificationsIds);
	Map<Qualification, Integer> getByOrder(Order order);
}
