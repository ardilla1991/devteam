package by.htp.devteam.bean.dto;

import java.util.List;
import java.util.Map;

import by.htp.devteam.bean.Order;
import by.htp.devteam.bean.Qualification;
import by.htp.devteam.bean.Work;

public class OrderDto {
	
	private Order order;
	private List<Work> works;
	private Map<Qualification, Integer> qualifications;
	
	public Order getOrder() {
		return order;
	}
	
	public void setOrder(Order order) {
		this.order = order;
	}
	
	public List<Work> getWorks() {
		return works;
	}
	
	public void setWorks(List<Work> works) {
		this.works = works;
	}
	
	public Map<Qualification, Integer> getQualifications() {
		return qualifications;
	}
	
	public void setQualifications(Map<Qualification, Integer> qualifications) {
		this.qualifications = qualifications;
	}
	
	@Override
	public String toString() {
		return "OrderDto [order=" + order + ", works=" + works + ", qualifications=" + qualifications + "]";
	}
	
}
