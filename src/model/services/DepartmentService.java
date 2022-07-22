package model.services;

import java.util.List;

import db.DbException;
import db.dao.DaoFactory;
import db.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentService {

	DepartmentDao dao = DaoFactory.createDepartmentDao();
	
	public void insert(Department department) {
		for (Department nameCheck : this.findAll()) {
			if (nameCheck.getName() == department.getName()) {
				throw new DbException("Department already exists");
			}
		}
		dao.insert(department);
	}
	
	public void update(Department department) {
		dao.update(department);
	}
	
	public void deleteById(Integer id) {
		dao.deleteById(id);
	}
	
	public Department findById(Integer id) {
		return dao.findById(id);
	}
	
	public List<Department> findAll() {
		return dao.findAll();
	}
}
