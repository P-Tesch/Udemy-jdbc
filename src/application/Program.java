package application;

import dao.DaoFactory;
import dao.SellerDao;
import entities.Department;
import entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		SellerDao sellerDao = DaoFactory.createSellerDao();
		
		System.out.println("--FindById = 1--");
		System.out.println(sellerDao.findById(1));
		System.out.println();
		
		Department department = new Department(null, 2);
		System.out.println("--FindByDepartment = " + department + "--");
		for (Seller seller : sellerDao.findByDepartment(department)) {
			System.out.println(seller);
		}
		System.out.println();
		
		System.out.println("--FindAll--");
		for (Seller seller : sellerDao.findAll()) {
			System.out.println(seller);
		}
		
	}

}
