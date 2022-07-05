package dao;

import dao.impl.DepartmentDaoJDBC;
import dao.impl.SellerDaoJDBC;

public class DaoFactory {

	public SellerDao createSellerDao() {
		return new SellerDaoJDBC();
	}
	
	public DepartmentDao createDepartmentDao() {
		return new DepartmentDaoJDBC();
	}
}
