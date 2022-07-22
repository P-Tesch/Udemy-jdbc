package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Program;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.DepartmentService;

public class MainViewController implements Initializable {
	
	@FXML
	private MenuItem menuItemSeller;
	@FXML
	private MenuItem menuItemDepartment;
	@FXML
	private MenuItem menuItemAbout;
	@FXML
	private Button buttonSeller;
	@FXML
	private Button buttonDepartment;
	
	@FXML
	public void onMenuItemSellerAction() {
		this.loadView(null, x -> {});
	}
	
	@FXML
	public void onMenuItemDepartmentAction() {
		this.loadView("/gui/DepartmentView.fxml", (DepartmentViewController controller) -> {
			controller.setDepartmentService(new DepartmentService());
			controller.updateTableViewDepartment();
		});
	}
	
	@FXML
	public void onMenuItemAboutAction() {
		Alerts.showAlert("About", null, "JavaFX and JDBC demo application", AlertType.INFORMATION);
	}
	
	@FXML
	public void onButtonSellerAction() {
		this.loadView(null, x -> {});
	}
	
	@FXML
	public void onButtonDepartmentAction() {
		this.loadView("/gui/DepartmentView.fxml", (DepartmentViewController controller) -> {
			controller.setDepartmentService(new DepartmentService());
			controller.updateTableViewDepartment();
		});
	}


	@Override
	public void initialize(URL url, ResourceBundle rb) {
	}
	
	private synchronized <T> void loadView(String viewPath, Consumer<T> initializationFunction) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(viewPath));
			VBox toLoadVbox = loader.load();
			Scene mainScene = Program.getMainScene();
			VBox mainVbox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			Node headerMenu = mainVbox.getChildren().get(0);
			
			mainVbox.getChildren().clear();
			mainVbox.getChildren().add(headerMenu);
			mainVbox.getChildren().addAll(toLoadVbox.getChildren());
			
			initializationFunction.accept(loader.getController());
		}
		catch (IOException e) {
			Alerts.showAlert("Error", null, e.getMessage(), AlertType.ERROR);
			e.printStackTrace();
		}
	}
}
