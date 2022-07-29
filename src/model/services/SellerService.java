package model.services;

import java.util.List;

import db.dao.DaoFactory;
import db.dao.SellerDao;
import model.entities.Seller;
import model.exceptions.ModelException;

public class SellerService {

	SellerDao dao = DaoFactory.createSellerDao();
	
	public void insertOrUpdate(Seller seller) {
		if (seller.getName() == null || seller.getName().isEmpty() || seller.getName().isBlank()) {
			throw new ModelException("Seller must have a name");
		}
		if (seller.getEmail() == null || seller.getEmail().isEmpty() || seller.getEmail().isBlank()) {
			throw new ModelException("Seller must have a email");
		}
		if (seller.getBirthDate() == null) {
			throw new ModelException("Seller must have a birth date");
		}
		if (seller.getBaseSalary() == null || seller.getBaseSalary() == 0.0) {
			throw new ModelException("Seller must have a salary");
		}
		for (Seller emailCheck : this.findAll()) {
			if (emailCheck.getEmail().equalsIgnoreCase(seller.getName())) {
				throw new ModelException("Seller with same email already exists");
			}
		}
		if (seller.getId() == null) {
			dao.insert(seller);
		}
		else {
			dao.update(seller);
		}
	}
	
	public void delete(Seller seller) {
		dao.deleteById(seller.getId());
	}
	
	public Seller findById(Integer id) {
		return dao.findById(id);
	}
	
	public List<Seller> findAll() {
		return dao.findAll();
	}
}
