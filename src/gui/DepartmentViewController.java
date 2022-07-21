package gui;

import java.net.URL;
import java.util.ResourceBundle;

import application.Program;
import dao.DaoFactory;
import dao.DepartmentDao;
import entities.Department;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class DepartmentViewController implements Initializable {
	
	private DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
	private ObservableList<Department> departmentList;
	
	@FXML
	private Button buttonNew;
	
	@FXML
	private Button buttonDelete;
	
	@FXML
	private TableView<Department> tableViewDepartment;
	
	@FXML
	private TableColumn<Department, Integer> tableColumnId;
	
	@FXML
	private TableColumn<Department, String> tableColumnName;
	
	@FXML
	public void onBtNewAction() {
		System.out.println("a");
	}
	
	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}
 
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		this.tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		this.tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		this.tableViewDepartment.prefHeightProperty().bind(( (Stage) Program.getMainScene().getWindow()).heightProperty());
		this.updateTableViewDepartment();
	}

	private void updateTableViewDepartment() {
		if (this.departmentDao == null) {
			throw new IllegalStateException("DepartmentDao is null");
		}
		this.departmentList = FXCollections.observableArrayList(departmentDao.findAll());
		this.tableViewDepartment.setItems(departmentList);
	}
}
