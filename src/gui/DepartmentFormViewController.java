package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;
import model.exceptions.ModelException;
import model.services.DepartmentService;

public class DepartmentFormViewController implements Initializable {
	
	Department department;
	DepartmentService departmentService;
	List<DataChangeListener> dataChangeListeners;
	
	@FXML
	TextField textFieldName;
	
	@FXML
	Button buttonConfirm;
	
	@FXML
	Button buttonCancel;
	
	@FXML
	Label labelId;
	
	@FXML
	Label labelError;
	
	@FXML
	public void onButtonConfirmAction(ActionEvent event) {
		if (this.department == null) {
			throw new IllegalStateException("Department is null");
		}
		if (this.departmentService == null) {
			throw new IllegalStateException("DepartmentService is null");
		}
		try {
			department.setId(Utils.tryParseToInt(labelId.getText()));
			department.setName(textFieldName.getText());
			departmentService.insertOrUpdate(department);
			this.notifyDataChangeListeners();
			Utils.currentStage(event).close();
		}
		catch (DbException e) {
			Alerts.showAlert("Db error", null, e.getMessage(), AlertType.ERROR);
		}
		catch (ModelException e) {
			Alerts.showAlert("Model error", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	@FXML
	public void onButtonCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		Constraints.setTextFieldMaxLength(textFieldName, 30);
		this.dataChangeListeners = new ArrayList<>();
	}
	
	public void setDepartment(Department department) {
		this.department = department;
		labelId.setText((department.getId() == null) ? "Automatic" : String.valueOf(department.getId()));
		textFieldName.setText(department.getName());
	}
	
	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}
	
	public void addDataChangeListener(DataChangeListener listener) {
		this.dataChangeListeners.add(listener);
	}
	
	private void notifyDataChangeListeners() {
		for (DataChangeListener listener : this.dataChangeListeners) {
			listener.onDataChange();
		}
	}
}
