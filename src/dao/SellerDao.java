package dao;

import java.util.List;

import entities.Department;
import entities.Seller;

public interface SellerDao {
	void insert(Seller obj);
	void update(Seller obj);
	void deleteById(Integer id);
	Department findById(Integer id);
	List<Seller> findAll();
}
