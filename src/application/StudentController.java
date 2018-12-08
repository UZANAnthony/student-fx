package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class StudentController implements Initializable {
	@FXML
	ListView<String> lvStudents;
	@FXML
	TextField tfName;
	@FXML
	ComboBox<String> cmbGender;
	@FXML
	DatePicker dpDate;
	@FXML
	TextField tfMark;
	@FXML
	TextArea taComments;
	@FXML
	ImageView ivPhoto;
	@FXML
	Button btnEdit;
	@FXML
	Button btnSave;
	@FXML
	Button btnCancel;
	@FXML
	Button btnAdd;
	@FXML
	Button btnDelete;
	@FXML 
	Label lblPhoto;
	
	DBManager manager;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		List<String> gvalues = new ArrayList<String>(); 
		gvalues.add("Male"); 
		gvalues.add("Female"); 
		ObservableList<String> gender = FXCollections.observableArrayList(gvalues); 
		cmbGender.setItems(gender);
		btnEdit.setDisable(true);
		btnSave.setDisable(true);
		btnCancel.setDisable(true);
		btnAdd.setDisable(false);
		btnDelete.setDisable(true);
		lblPhoto.setVisible(false);
		tfName.setEditable(false);
		cmbGender.setEditable(false);
		dpDate.setEditable(false);
		tfMark.setEditable(false);
		taComments.setEditable(false);
		manager = new DBManager();
		
		fetchStudents();
		
		lvStudents.getSelectionModel().selectedItemProperty().addListener(e-> displayStudentDetails(lvStudents.getSelectionModel().getSelectedItem()));
	}
	
	public void fetchStudents() { 
		ObservableList<String> students; 
		if (manager.loadStudents()!=null) { 
			students= FXCollections.observableArrayList(manager.loadStudents()); 
			lvStudents.setItems(students); 
		} 
	} 
	
	private void displayStudentDetails(String name) {
		try{ 
			Student s = manager.fetchStudentByName(name);
			tfName.setText(s.getName()); 
			cmbGender.setValue(s.getGender()); 
			dpDate.setValue(s.getBirthDate());
			Image image; 
			InputStream is = null;        
			if(s.getPhoto()!=null) {            
				is = new FileInputStream(s.getPhoto());           
				image = new Image(is);            
				ivPhoto.setImage(image); 
			}else {
				// Chemin correpsond à la photo pour les élèves sans photo
				is = new FileInputStream("C:\\Users\\Akz\\Desktop\\JAVAEE\\StudentFX\\Cinema-Human-Head-icon.png");    
				image= new Image (is);    
				ivPhoto.setImage(image);       
			} 
			tfMark.setText(String.valueOf(s.getMark())); 
			taComments.setText(s.getComments());
			btnEdit.setDisable(false);
			btnSave.setDisable(true);
			btnCancel.setDisable(true);
			btnDelete.setDisable(false);
		} catch (Exception e) { 
				System.out.println(e.getMessage()); 
		} 
	}
	
	public void onEdit() {
		cmbGender.setEditable(true);
		dpDate.setEditable(true);
		tfMark.setEditable(true);
		taComments.setEditable(true);
		btnSave.setDisable(false);
		btnCancel.setDisable(false);
		btnAdd.setDisable(true);
		btnDelete.setDisable(true);
	}
	
	public void onCancel() {
		btnSave.setDisable(true);
		btnCancel.setDisable(true);
		btnAdd.setDisable(false);
		tfName.setEditable(false);
		cmbGender.setEditable(false);
		dpDate.setEditable(false);
		tfMark.setEditable(false);
		taComments.setEditable(false);
		fetchStudents();
	}
	
	public void chooseImage() throws FileNotFoundException {
		Image image;   
		InputStream is = null;  
		FileChooser fc = new FileChooser();
		File selectedFile = fc.showOpenDialog(null);
		if(selectedFile != null) {
			is = new FileInputStream(selectedFile.getPath());
			lblPhoto.setText(selectedFile.getPath().toString());
			image= new Image (is);    
			ivPhoto.setImage(image);
			btnSave.setDisable(false);
		}
	} 
	
	public void onSave() {
		if(btnEdit.isDisable()){
			Student s = new Student();
			s.setName(tfName.getText());
			s.setGender(cmbGender.getValue());
			s.setBirthDate(dpDate.getValue());
			if(lblPhoto.getText() != "") {
				s.setPhoto(lblPhoto.getText());
			}
			s.setMark(tfMark.getText());
			s.setComments(taComments.getText());
			manager.addStudent(s.getName(), s.getGender(), s.getBirthDate(), s.getPhoto(), s.getMark(), s.getComments());
			fetchStudents();
		}
		else {
			Student s = manager.fetchStudentByName(tfName.getText());
			if(cmbGender.getValue() != null)
				s.setGender(cmbGender.getValue());
			if(dpDate.getValue() != null)
				s.setBirthDate(dpDate.getValue());
			if(lblPhoto.getText() != "") {
				s.setPhoto(lblPhoto.getText());
			}
			if(tfMark.getText() != null)
				s.setMark(tfMark.getText());
			if(taComments.getText() != null)
				s.setComments(taComments.getText());
			manager.updateStudent(tfName.getText(), s.getGender(), s.getBirthDate(), s.getPhoto(), s.getMark(), s.getComments());
		}
		cmbGender.setEditable(false);
		dpDate.setEditable(false);
		tfMark.setEditable(false);
		taComments.setEditable(false);
		onCancel();
	}
	
	public void onNew() {
		tfName.setText("");
		tfMark.setText("");
		taComments.setText("");
		lblPhoto.setText("");
		dpDate.setValue(null);
		ivPhoto.setImage(null);
		cmbGender.setEditable(true);
		dpDate.setEditable(true);
		tfMark.setEditable(true);
		taComments.setEditable(true);
		tfName.setEditable(true);
		btnEdit.setDisable(true);
		btnDelete.setDisable(true);
		btnSave.setDisable(false);
		btnCancel.setDisable(false);
	}
	
	public void onDelete() {
		Student s = manager.fetchStudentByName(tfName.getText());
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Deletion process");
		alert.setHeaderText("It's your last chance !");
		alert.setContentText("Are you sure to delete this student ?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			manager.deleteStudent(s.getName());
			fetchStudents();
		} else {
		    onCancel();
		}
	}
}
