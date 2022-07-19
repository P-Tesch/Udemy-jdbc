package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;

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
		System.out.println("a");
	}
	
	@FXML
	public void onMenuItemDepartmentAction() {
		System.out.println("a");
	}
	
	@FXML
	public void onMenuItemAboutAction() {
		System.out.println("a");
	}
	
	@FXML
	public void onButtonSellerAction() {
		System.out.println("a");
	}
	
	@FXML
	public void onButtonDepartmentAction() {
		System.out.println("a");
	}


	@Override
	public void initialize(URL url, ResourceBundle rb) {

	}

}
