package by.htp.devteam.bean.vo;

import java.util.List;
import java.util.Map;

import by.htp.devteam.bean.Order;
import by.htp.devteam.bean.Qualification;
import by.htp.devteam.bean.Work;

/**
 * Order's object. Use for display object with all information for chosen works, 
 * qualifications with chosen numbers of employees.
 * Has properties <b>order</b>, <b>works</b>, 
 * <b>qualifications</b>
 * @author julia
 *
 */
public class OrderVo {
	
	/** Order's object */
	private Order order;
	
	/** List of works chosed in order */
	private List<Work> works;
	
	/** List of qualifications with numbers of employees chosed in order */
	private Map<Qualification, Integer> qualifications;
	
	public OrderVo() {
		super();
	}
	
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
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((order == null) ? 0 : order.hashCode());
		result = PRIME * result + ((qualifications == null) ? 0 : qualifications.hashCode());
		result = PRIME * result + ((works == null) ? 0 : works.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderVo other = (OrderVo) obj;
		if (order == null) {
			if (other.order != null)
				return false;
		} else if (!order.equals(other.order))
			return false;
		if (qualifications == null) {
			if (other.qualifications != null)
				return false;
		} else if (!qualifications.equals(other.qualifications))
			return false;
		if (works == null) {
			if (other.works != null)
				return false;
		} else if (!works.equals(other.works))
			return false;
		return true;
	}
	
}
