package gui;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;
import model.entities.Seller;
import model.exceptions.ModelException;
import model.services.DepartmentService;
import model.services.SellerService;

public class SellerFormViewController implements Initializable {
	
	Seller seller;
	SellerService sellerService;
	DepartmentService departmentService;
	List<DataChangeListener> dataChangeListeners;
	
	@FXML
	TextField textFieldName;
	
	@FXML
	TextField textFieldEmail;
	
	@FXML
	DatePicker datePickerBirtDate;
	
	@FXML
	TextField textFieldBaseSalary;
	
	@FXML
	ComboBox<Department> ComboBoxDepartment;
	
	@FXML
	Button buttonConfirm;
	
	@FXML
	Button buttonCancel;
	
	@FXML
	Button buttonDelete;
	
	@FXML
	Label labelId;
	
	@FXML
	public void onButtonConfirmAction(ActionEvent event) {
		if (this.seller == null) {
			throw new IllegalStateException("seller is null");
		}
		if (this.sellerService == null) {
			throw new IllegalStateException("sellerService is null");
		}
		try {
			seller.setId(Utils.tryParseToInt(labelId.getText()));
			seller.setName(textFieldName.getText());
			seller.setEmail(textFieldEmail.getText());
			seller.setBirthDate(Date.from(Instant.from(datePickerBirtDate.getValue().atStartOfDay(ZoneId.systemDefault()))));
			seller.setBaseSalary(Utils.tryParseToDouble(textFieldBaseSalary.getText()));
			seller.setDepartment(ComboBoxDepartment.getValue());
			sellerService.insertOrUpdate(seller);
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
	
	@FXML
	public void onButtonDeleteAction(ActionEvent event) {
		if (this.seller== null) {
			throw new IllegalStateException("seller is null");
		}
		if (this.sellerService == null) {
			throw new IllegalStateException("sellerService is null");
		}
		try {
			Optional<ButtonType> choice = Alerts.showConfirmation("Deletion", "Are you sure you want to delete the seller" + this.seller.getName() + "?");
			if (choice.get() == ButtonType.OK) {
				sellerService.delete(seller);
				this.notifyDataChangeListeners();
				Utils.currentStage(event).close();
			}
		}
		catch (DbException e) {
			Alerts.showAlert("Db error", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	public void setSeller(Seller seller) {
		if (this.departmentService == null) {
			throw new IllegalStateException("DepartmentService is null");
		}
		Locale.setDefault(Locale.US);
		this.seller = seller;
		this.labelId.setText((seller.getId() == null) ? "Automatic" : String.valueOf(seller.getId()));
		this.textFieldName.setText(seller.getName());
		this.textFieldEmail.setText(seller.getEmail());
		if (seller.getBaseSalary() != null) {
			this.datePickerBirtDate.setValue(LocalDate.ofInstant(seller.getBirthDate().toInstant(), ZoneId.systemDefault()));
		}
		this.textFieldBaseSalary.setText(String.format(String.valueOf(seller.getBaseSalary()), "%.2f"));
		this.ComboBoxDepartment.setItems(FXCollections.observableArrayList(departmentService.findAll()));
		this.ComboBoxDepartment.setValue(seller.getDepartment());
		this.buttonDelete.setVisible(seller.getId() != null);
	}
	
	public void setSellerService(SellerService sellerService) {
		this.sellerService = sellerService;
	}
	
	public void setDepartmentSerivce(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}
	
	public void addDataChangeListener(DataChangeListener listener) {
		this.dataChangeListeners.add(listener);
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		Constraints.setTextFieldMaxLength(textFieldName, 80);
		Constraints.setTextFieldMaxLength(textFieldEmail, 80);
		Constraints.setTextFieldDouble(textFieldBaseSalary);
		Constraints.setTextFieldMaxLength(textFieldBaseSalary, 20);
		Utils.formatDatePicker(datePickerBirtDate, "dd/MM/yyyy");
		Utils.formatComboBoxDepartment(ComboBoxDepartment);
		this.dataChangeListeners = new ArrayList<>();
	}
	
	private void notifyDataChangeListeners() {
		for (DataChangeListener listener : this.dataChangeListeners) {
			listener.onDataChange();
		}
	}
}
