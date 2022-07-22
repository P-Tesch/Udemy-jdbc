package db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import db.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao {
	
	private Connection conn;
	
	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Department department) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"INSERT INTO department "
				+ "(Name) "
				+ "VALUES "
				+ "(?)"
				, Statement.RETURN_GENERATED_KEYS);
			st.setString(1, department.getName());
			
			int success = st.executeUpdate();
			if (success > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					department.setId(rs.getInt(1));
				}
				DB.closeResultSet(rs);
			}
			else {
				throw new DbException("Data insertion unsuccessful");
			}
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Department department) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"UPDATE department "
				+ "SET Name = ? "
				+ "WHERE Id = ?");
			st.setString(1, department.getName());
			st.setInt(2, department.getId());
			
			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"DELETE FROM department "
				+ "WHERE Id = ?");
			st.setInt(1, id);
			
			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Department findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					  "SELECT * FROM department "
					+ "WHERE department.Id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				return this.instantiateDepartment(rs);
			}
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		return null;
	}

	@Override
	public List<Department> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					  "SELECT * FROM department "
					+ "ORDER BY Name");
			rs = st.executeQuery();
			
			List<Department> departments = new ArrayList<>();
			
			while (rs.next()) {
				departments.add(this.instantiateDepartment(rs));
			}
			return departments;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		return new Department(
				rs.getString("Name"), 
				rs.getInt("Id"));
	}
}
