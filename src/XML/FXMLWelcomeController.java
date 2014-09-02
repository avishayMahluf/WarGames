package XML;

import com.sun.org.apache.bcel.internal.generic.LUSHR;

import WarWeapons.Destructor;
import WarWeapons.Launcher;
import WarWeapons.Missile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FXMLWelcomeController {

	@FXML private Text answer;
	@FXML private TextField MissileId;
	@FXML private TextField toLauncher;
	@FXML private TextField destination;
	@FXML private TextField damage;
	@FXML private TextField LauncherId;
	@FXML private TextField DestructorId;
	@FXML private TextField DestructorType;
	@FXML private Button add;
	
	@FXML private ComboBox<String> comboBox1;
	
	

	@FXML protected void addMissile(ActionEvent event) {
		answer.setText("Added");
		System.out.println("id = " + MissileId.getText()
				+ " destination = " + destination.getText() 
				+ " damage = " + damage.getText()); 
//		Missile missile = new Missile(MissileId.getText(), destination.getText(),
//				0, 5, damage.getText(), TheLauncher);
//		missile.toString();
		Stage stage = (Stage) add.getScene().getWindow();
		stage.close();
	}

	@FXML protected void addLauncher(ActionEvent event) {
		answer.setText("Added");
		System.out.println("id = " + LauncherId.getText());
		Launcher launcher = new Launcher(LauncherId.getText());
		System.out.println(launcher.toString());
		Stage stage = (Stage) add.getScene().getWindow();
		stage.close();
	}

	@FXML protected void addDestructor(ActionEvent event) {
		answer.setText("Added");
		System.out.println("id = " + DestructorId.getText()
				+ " type = " + DestructorType.getText());
		Destructor destructor = new Destructor(Destructor.Type.valueOf(DestructorType.getText()));
		System.out.println(destructor.toString());
		Stage stage = (Stage) add.getScene().getWindow();
		stage.close();
	}
	
}
