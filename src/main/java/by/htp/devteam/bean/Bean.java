package by.htp.devteam.bean;

import java.io.Serializable;

/**
 * Bean object. Has properties <b>id</b>, <b>name</b>, 
 * <b>email</b>, <b>phone</b>, <b>user</b>
 * Implement Serializable, because object is saved in session
 * @author julia
 *
 */
public abstract class Bean implements Serializable {

	private static final long serialVersionUID = 4533901538327117639L;
	/** Identifier for record */
	protected Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
