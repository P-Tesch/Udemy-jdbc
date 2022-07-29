package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Program;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.skin.TableColumnHeader;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentViewController implements Initializable, DataChangeListener {
	
	private DepartmentService departmentService;
	private ObservableList<Department> departmentList;
	
	@FXML
	private Button buttonNew;
	
	@FXML
	private TableView<Department> tableViewDepartment;
	
	@FXML
	private TableColumn<Department, Integer> tableColumnId;
	
	@FXML
	private TableColumn<Department, String> tableColumnName;
	
	@FXML
	public void onBtNewAction(ActionEvent event) {
		this.createDepartmentFormView(new Department(), "/gui/DepartmentFormView.fxml", Utils.currentStage(event));
	}
	
	@FXML
	public void onTableViewMouseClicked(MouseEvent event) {
		Department department = this.tableViewDepartment.getSelectionModel().getSelectedItem();
		if (department != null 
			&& event.getButton() == MouseButton.PRIMARY 
			&& event.getClickCount() == 2 
			&& !(event.getTarget() instanceof TableColumnHeader)
			&& !(event.getTarget() instanceof TableRow)
			&& !(event.getTarget().toString().equals("TableColumn$1$1[id=tableColumnName, styleClass=cell indexed-cell table-cell table-column]'null'"))
			&& !(event.getTarget().toString().equals("TableColumn$1$1[id=tableColumnId, styleClass=cell indexed-cell table-cell table-column]'null'"))) {
				this.createDepartmentFormView(department, "/gui/DepartmentFormView.fxml", Utils.currentStage(event));
		}
	}
	
	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}
 
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		this.tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		this.tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		this.tableViewDepartment.prefHeightProperty().bind(( (Stage) Program.getMainScene().getWindow()).heightProperty());
	}

	@Override
	public void onDataChange() {
		this.updateTableViewDepartment();
	}
	
	public void updateTableViewDepartment() {
		if (this.departmentService == null) {
			throw new IllegalStateException("DepartmentService is null");
		}
		this.departmentList = FXCollections.observableArrayList(departmentService.findAll());
		this.tableViewDepartment.setItems(departmentList);
		this.tableViewDepartment.refresh();
	}
	
	private void createDepartmentFormView(Department department, String viewPath, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(viewPath));
			Pane pane = loader.load();
			
			DepartmentFormViewController controller = loader.getController();
			controller.setDepartmentService(this.departmentService);
			controller.setDepartment(department);
			controller.addDataChangeListener(this);
			
			Stage formStage = new Stage();
			formStage.setTitle("Enter department data");
			formStage.setScene(new Scene(pane));
			formStage.setResizable(false);
			formStage.initOwner(parentStage);
			formStage.initModality(Modality.WINDOW_MODAL);
			formStage.showAndWait();
		}
		catch (IOException e) {
			Alerts.showAlert("Error", null, e.getMessage(), AlertType.ERROR);
		}
	}
}
