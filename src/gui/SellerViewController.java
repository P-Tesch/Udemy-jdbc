package gui;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
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
import model.entities.Seller;
import model.services.DepartmentService;
import model.services.SellerService;

public class SellerViewController implements Initializable, DataChangeListener {
	
	private SellerService sellerService;
	private ObservableList<Seller> sellerList;
	
	@FXML
	private Button buttonNew;
	
	@FXML
	private TableView<Seller> tableViewSeller;
	
	@FXML
	private TableColumn<Seller, Integer> tableColumnId;
	
	@FXML
	private TableColumn<Seller, String> tableColumnName;
	
	@FXML
	private TableColumn<Seller, String> tableColumnEmail;
	
	@FXML
	private TableColumn<Seller, Date> tableColumnBirthDate;
	
	@FXML
	private TableColumn<Seller, Double> tableColumnBaseSalary;
	
	@FXML
	private TableColumn<Seller, Department> tableColumnDepartmentName;
	
	@FXML
	public void onBtNewAction(ActionEvent event) {
		this.createSellerFormView(new Seller(), "/gui/SellerFormView.fxml", Utils.currentStage(event));
	}
	
	@FXML
	public void onTableViewMouseClicked(MouseEvent event) {
		Seller seller = this.tableViewSeller.getSelectionModel().getSelectedItem();
		if (seller != null 
			&& event.getButton() == MouseButton.PRIMARY 
			&& event.getClickCount() == 2 
			&& !(event.getTarget() instanceof TableColumnHeader)
			&& !(event.getTarget() instanceof TableRow)
			&& !(event.getTarget().toString().equals("TableColumn$1$1[id=tableColumnName, styleClass=cell indexed-cell table-cell table-column]'null'"))
			&& !(event.getTarget().toString().equals("TableColumn$1$1[id=tableColumnId, styleClass=cell indexed-cell table-cell table-column]'null'"))) {
				this.createSellerFormView(seller, "/gui/SellerFormView.fxml", Utils.currentStage(event));
		}
	}
	
	public void setSellerService(SellerService sellerService) {
		this.sellerService = sellerService;
	}
 
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		this.tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		this.tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		this.tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		this.tableColumnBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
		this.tableColumnBaseSalary.setCellValueFactory(new PropertyValueFactory<>("baseSalary"));
		this.tableColumnDepartmentName.setCellValueFactory(new PropertyValueFactory<>("department"));
		
		Utils.formatTableColumnDate(this.tableColumnBirthDate, "dd/MM/yyyy");
		Utils.formatTableColumnDouble(this.tableColumnBaseSalary, 2);
		Utils.formatTableColumnDepartment(this.tableColumnDepartmentName);
		
		this.tableViewSeller.prefHeightProperty().bind(( (Stage) Program.getMainScene().getWindow()).heightProperty());
	}

	@Override
	public void onDataChange() {
		this.updateTableViewSeller();
	}
	
	public void updateTableViewSeller() {
		if (this.sellerService == null) {
			throw new IllegalStateException("SellerService is null");
		}
		this.sellerList = FXCollections.observableArrayList(sellerService.findAll());
		this.tableViewSeller.setItems(sellerList);
	}
	
	private void createSellerFormView(Seller seller, String viewPath, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(viewPath));
			Pane pane = loader.load();
			
			SellerFormViewController controller = loader.getController();
			controller.setSellerService(this.sellerService);
			controller.setDepartmentSerivce(new DepartmentService());
			controller.setSeller(seller);
			controller.addDataChangeListener(this);
			
			Stage formStage = new Stage();
			formStage.setTitle("Enter seller data");
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
