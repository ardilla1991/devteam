package by.htp.devteam.service;

import by.htp.devteam.bean.dto.OrderDto;

public interface OrderService {
	OrderDto getNewOrders(String currPage) throws ServiceException;
}
