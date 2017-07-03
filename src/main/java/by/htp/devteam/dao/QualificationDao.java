package by.htp.devteam.dao;

import java.util.List;

import by.htp.devteam.bean.Qualification;

public interface QualificationDao {
	List<Qualification> fetchAll() throws DaoException;
}
