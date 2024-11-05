package com.zenveus.the_culinary_academy.controllers;

import com.jfoenix.controls.JFXButton;
import com.zenveus.the_culinary_academy.bo.BOFactory;
import com.zenveus.the_culinary_academy.bo.custom.UserBO;
import com.zenveus.the_culinary_academy.dto.UserDto;
import com.zenveus.the_culinary_academy.tm.UserTm;
import com.zenveus.the_culinary_academy.util.Regex;
import com.zenveus.the_culinary_academy.util.TextFields;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EmployeeController implements Initializable {

    public AnchorPane employeeRegMainAnchor;
    public ImageView reportLeftRightImage;

    // Employee Fields
    public TextField employeeIDField;
    public TextField employeeEmailField;
    public TextField employeeNameField;
    public TextField employeePhoneField;
    public TextField employeeAddressField;
    // Employee Search Field
    public TextField searchEmployee;
    // Employee Side Pane Title
    public Text sidePaneTitle;
    public TableView<UserTm> userTable;
    public TableColumn<?,?> colUsrId;
    public TableColumn<?,?> colUsrName;
    public TableColumn<?,?> colUsrEmail;
    public TableColumn<?,?> colUsrPhone;
    public TableColumn<?, ?> colUsrAddress;
    public UserTm selectedItem;
    private TranslateTransition sideTransition;
    private boolean isShow = false;

    ObservableList<UserTm> obList = FXCollections.observableArrayList();
    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTransition();
        setEmployeeID();
        setCellValueFactories();
        loadAllEmployees();
    }

    private void setEmployeeID() {
        String lastUser = getLastUserId();
        employeeIDField.setText(lastUser);
        employeeIDField.setDisable(true);
    }

    private String getLastUserId() {
    List<UserDto> allUsers = userBO.getAllUsers();

    if (allUsers.isEmpty()) {
        return "U001";
    }

    String lastUserId = allUsers.get(allUsers.size() - 1).getUserId();
    if (lastUserId == null || lastUserId.isEmpty() || !lastUserId.matches("U\\d+")) {
        return "U001";
    }

    int id = Integer.parseInt(lastUserId.substring(1));
    id++;

    return String.format("U%03d", id);
}


    //    side menu transition
    private void setTransition() {
        sideTransition = new TranslateTransition(Duration.seconds(1.5), employeeRegMainAnchor);
        sideTransition.setFromX(0);
        sideTransition.setToX(550); // Set initial `toX` based on `isShow`
        updateIcon();
    }
    //   update side menu icon
    private void updateIcon() {
        String iconPath = isShow
                ? "src/main/resources/com/zenveus/the_culinary_academy/image/icons/rightArow.png"
                : "src/main/resources/com/zenveus/the_culinary_academy/image/icons/leftArow.png";
        Image image = new Image(new File(iconPath).toURI().toString());
        reportLeftRightImage.setImage(image);
    }
    //   show hide side menu
    public void employeeAddPaneShowHideBtn(ActionEvent actionEvent) {
        isShow = !isShow;
        sideTransition.setDuration(Duration.seconds(isShow ? 1.5 : 2));
        sideTransition.setToX(isShow ? 550 : 0);
        updateIcon();
        sideTransition.play();
    }
    //  employee back btn (search bar)
    public void employeeBackBtn(ActionEvent actionEvent) {
        System.out.println("click employee page back Btn");

        // load dashboard page after clearing employeeRegMainAnchor
        DashboardController dashboardController = new DashboardController();
        dashboardController.loadDashboard(employeeRegMainAnchor);

    }
    // employee search filed enter click (search bar)
    public void searchEmployeeClick(ActionEvent actionEvent) {
        System.out.println("click employee search filed");

        String searchText = searchEmployee.getText();

        if(searchText.isEmpty()){
            loadAllEmployees();
            return;
        }

        List<UserDto> allUsers = userBO.getAllUsers();
        obList.clear();
        for (UserDto userDto : allUsers) {
            if(userDto.getUserId().contains(searchText) || userDto.getFullName().contains(searchText) || userDto.getEmail().contains(searchText) || userDto.getPhoneNumber().contains(searchText) || userDto.getAddress().contains(searchText)){
                obList.add(new UserTm(userDto.getUserId(), userDto.getFullName(), userDto.getEmail(), userDto.getPhoneNumber(), userDto.getAddress()));
            }
        }
        userTable.setItems(obList);

    }
    // employee search clear btn (search bar)
    public void searchEmployeeClearBtn(ActionEvent actionEvent) {
        System.out.println("click employee create Btn");

        searchEmployee.setText("");
    }

    // employee delete btn
    public void employeeDeleteBtn(ActionEvent actionEvent) {
        System.out.println("click employee delete Btn");

        UserDto user = new UserDto();
        user.setUserId(employeeIDField.getText());
        user.setFullName(employeeNameField.getText());
        user.setEmail(employeeEmailField.getText());
        user.setPhoneNumber(employeePhoneField.getText());
        user.setAddress(employeeAddressField.getText());

        UserDto userDto = userBO.isUserExist(user);

        if(userDto == null){
            new Alert(Alert.AlertType.ERROR, "Employee Not Found!").showAndWait();
            return;
        }

        boolean isDeleted = userBO.deleteUser(userDto);

        if(isDeleted){
            new Alert(Alert.AlertType.INFORMATION, "Employee Deleted Successfully!").showAndWait();
            setEmployeeID();
            clearAllFields();
            loadAllEmployees();
        }else{
            new Alert(Alert.AlertType.ERROR, "Failed to Delete Employee!").showAndWait();
        }
    }
    // employee save btn
    public void employeeSaveBtn(ActionEvent actionEvent) {
        System.out.println("click employee save Btn");

        String employeeID = employeeIDField.getText();
        String employeeEmail = employeeEmailField.getText();
        String employeeName = employeeNameField.getText();
        String employeePhone = employeePhoneField.getText();
        String employeeAddress = employeeAddressField.getText();
        String username = generateUsername(employeeName);
        String password = generatePassword(employeeName);

        UserDto userDTO = new UserDto();

        userDTO.setUserId(employeeID);
        userDTO.setFullName(employeeName);
        userDTO.setEmail(employeeEmail);
        userDTO.setPhoneNumber(employeePhone);
        userDTO.setAddress(employeeAddress);

        userDTO.setUsername(username);
        userDTO.setPassword(password);

        System.out.println(userDTO);

        if(!Regex.isTextFieldValid(TextFields.EMAIL, employeeEmail)){
            new Alert(Alert.AlertType.WARNING, "Invalid Email!").showAndWait();
            employeeEmailField.requestFocus();
            return;
        }

        if(!Regex.isTextFieldValid(TextFields.CONTACT, employeePhone)){
            new Alert(Alert.AlertType.WARNING, "Invalid Phone Number!").showAndWait();
            employeePhoneField.requestFocus();
            return;
        }

        // search and make sure no duplicate email
        boolean isEmailExist = isEmailExist(employeeEmail);
        if(isEmailExist){
            new Alert(Alert.AlertType.WARNING, "Email Already Exist!").showAndWait();
            employeeEmailField.requestFocus();
            return;
        }

        // search and make sure no duplicate phone number
        boolean isPhoneExist = isPhoneExist(employeePhone);
        if(isPhoneExist){
            new Alert(Alert.AlertType.WARNING, "Phone Number Already Exist!").showAndWait();
            employeePhoneField.requestFocus();
            return;
        }


        try {
            boolean isAdded = userBO.addUser(userDTO);
            if(isAdded){
                new Alert(Alert.AlertType.INFORMATION, "Employee Added Successfully!").showAndWait();
                setEmployeeID();
                clearAllFields();
                loadAllEmployees();
            }else{
                new Alert(Alert.AlertType.ERROR, "Failed to Add Employee!").showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String generatePassword(String employeeName) {
        String base = employeeName.split("\\s+")[0].toLowerCase();
        int randomNum = (int) (Math.random() * 10000);
        String specialChars = "!@#$%^&*";
        char specialChar = specialChars.charAt((int) (Math.random() * specialChars.length()));

        return base + randomNum + specialChar;
    }
    private String generateUsername(String employeeName) {
        // username is the first word of the full name
        return employeeName.split("\\s+")[0].toLowerCase();
    }

    private boolean isPhoneExist(String employeePhone) {
        // search and make sure no duplicate phone number
        List<UserDto> existingUsers = userBO.getAllUsers();

        for (UserDto userDto : existingUsers) {
            if(userDto.getPhoneNumber().equals(employeePhone)){
                return true;
            }
        }

        return false;
    }

    private boolean isEmailExist(String employeeEmail) {
        // search and make sure no duplicate email
        List<UserDto> existingUsers = userBO.getAllUsers();

        for (UserDto userDto : existingUsers) {
            if(userDto.getEmail().equals(employeeEmail)){
                return true;
            }
        }

        return false;
    }

    //clear all fields
    public void clearAllFields(){
        employeeEmailField.clear();
        employeeNameField.clear();
        employeePhoneField.clear();
        employeeAddressField.clear();
    }

    // employee update btn
    public void employeeUpdateBtn(ActionEvent actionEvent) {
        System.out.println("click employee update Btn");

        boolean isUpdated = userBO.updateUser(new UserDto(selectedItem.getUserId(), selectedItem.getFullName(), selectedItem.getEmail(), selectedItem.getPhoneNumber(), selectedItem.getAddress(), selectedItem.getUserId(), selectedItem.getUserId()));
        if(isUpdated){
            new Alert(Alert.AlertType.INFORMATION, "Employee Updated Successfully!").showAndWait();
            setEmployeeID();
            clearAllFields();
            loadAllEmployees();
        }else{
            new Alert(Alert.AlertType.ERROR, "Failed to Update Employee!").showAndWait();
        }

    }

    public void loadAllEmployees(){
        try {
            List<UserDto> allUsers = userBO.getAllUsers();
            obList.clear();
            for (UserDto userDto : allUsers) {
                obList.add(new UserTm(userDto.getUserId(), userDto.getFullName(), userDto.getEmail(), userDto.getPhoneNumber(), userDto.getAddress()));
            }
            userTable.setItems(obList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setCellValueFactories(){
        colUsrId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colUsrName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colUsrEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colUsrPhone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        colUsrAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
    }

    public void rowClick(MouseEvent mouseEvent) {
        System.out.println("click row");
        if (userTable.getSelectionModel().getSelectedItem() != null) {
            selectedItem = userTable.getSelectionModel().getSelectedItem();
            employeeIDField.setText(selectedItem.getUserId());
            employeeNameField.setText(selectedItem.getFullName());
            employeeEmailField.setText(selectedItem.getEmail());
            employeePhoneField.setText(selectedItem.getPhoneNumber());
            employeeAddressField.setText(selectedItem.getAddress());

            if(isShow){
                isShow = !isShow;
                sideTransition.setDuration(Duration.seconds(isShow ? 1.5 : 2));
                sideTransition.setToX(isShow ? 550 : 0);
                updateIcon();
                sideTransition.play();
            }
        }
    }
}
