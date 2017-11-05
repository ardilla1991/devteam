package by.htp.devteam.bean;

public class OrderQualification {

	private Order order;
	private Qualification qualification;
	private int count;
	
	public Order getOrder() {
		return order;
	}
	
	public void setOrder(Order order) {
		this.order = order;
	}
	
	public Qualification getQualification() {
		return qualification;
	}
	
	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}
	
	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + count;
		result = prime * result + ((order == null) ? 0 : order.hashCode());
		result = prime * result + ((qualification == null) ? 0 : qualification.hashCode());
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
		OrderQualification other = (OrderQualification) obj;
		if (count != other.count)
			return false;
		if (order == null) {
			if (other.order != null)
				return false;
		} else if (!order.equals(other.order))
			return false;
		if (qualification == null) {
			if (other.qualification != null)
				return false;
		} else if (!qualification.equals(other.qualification))
			return false;
		return true;
	}
	
	
}
