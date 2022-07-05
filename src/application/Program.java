package application;

import java.util.Date;

import entities.Department;
import entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		Department department = new Department("Electronics", 1);
		Seller seller = new Seller(1, "Jorje", "Jorje@gmail.com", new Date(), 2500.0, department);
		
		System.out.println(seller);

	}

}
