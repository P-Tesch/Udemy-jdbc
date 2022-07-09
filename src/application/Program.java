package application;

import java.util.Date;

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
		
		System.out.println("--Insert--");
		sellerDao.insert(new Seller(null, "Jorje", "Jorje@gmail.com", new Date(), 3500.0, new Department(null, 2)));
		System.out.println("Insert operation successful");
		System.out.println();
		
		System.out.println("--FindAll--");
		sellerDao.findAll().stream().forEach(System.out::println);
		System.out.println();
		
		System.out.println("--Update--");
		sellerDao.update(new Seller(7, "Jorge", "Jorge@gmail.com", new Date(), 4000.0, new Department(null, 1)));
		System.out.println("Update operation successful");
		System.out.println();
		
		System.out.println("--Delete--");
		sellerDao.deleteById(8);
		System.out.println("Delete operation successful");
		System.out.println();
		
		Department department = new Department(null, 1);
		System.out.println("--FindByDepartment = " + department + "--");
		sellerDao.findByDepartment(department).stream().forEach(System.out::println);
		System.out.println();
	}

}
