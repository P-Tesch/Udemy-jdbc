package model.services;

import java.util.List;

import db.dao.DaoFactory;
import db.dao.DepartmentDao;
import model.entities.Department;
import model.exceptions.ModelException;

public class DepartmentService {

	DepartmentDao dao = DaoFactory.createDepartmentDao();
	
	public void insertOrUpdate(Department department) {
		if (department.getName() == null || department.getName().isEmpty() || department.getName().isBlank()) {
			throw new ModelException("Department must have a name");
		}
		for (Department nameCheck : this.findAll()) {
			if (nameCheck.getName().equalsIgnoreCase(department.getName())) {
				throw new ModelException("Department already exists");
			}
		}
		if (department.getId() == null) {
			dao.insert(department);
		}
		else {
			dao.update(department);
		}
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
