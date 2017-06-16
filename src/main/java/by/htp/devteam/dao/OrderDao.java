package by.htp.devteam.dao;

import by.htp.devteam.bean.dto.OrderDto;

public interface OrderDao {
	OrderDto getNewOrders(int offset, int countPerPage);
}
