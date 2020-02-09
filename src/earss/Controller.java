/*
 * Copyright (C) 2018-2020 Novak Petrovic
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package earss;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * Form controller; this is where all action happens.
 */
public class Controller implements Initializable {
  ArrivalList arrivals;
  @FXML Button btnAddEmployee = new Button();
  @FXML Button btnClose = new Button();
  @FXML Button btnGenerateReport = new Button();
  @FXML Button btnRegisterArrival = new Button();
  @FXML Button btnRemoveEmployee = new Button();
  Employee emp;
  EmployeeList employees;
  @FXML Label lblEmployeeName = new Label();
  @FXML Label lblEmployeeRegistration = new Label();
  @FXML Label lblMessages = new Label();
  String listViewSelectedEmployee;
  @FXML ListView<String> lvEmployees = new ListView<String>();
  @FXML TextField tfEmployeeName = new TextField();

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    employees = new EmployeeList();
    try {
      employees.readEmployees();
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
    employees.sortEmployeeList();
    for (Employee it : employees.getEmployees()) {
      lvEmployees.getItems().add(it.getEmployeeName());
    }
    lvEmployees.getSelectionModel().selectedItemProperty().addListener(
        new ChangeListener<String>() {
          @Override
          public void changed(ObservableValue<? extends String> observable, String o, String n) {
            listViewSelectedEmployee = n;
            lblMessages.setText("");
            lblEmployeeRegistration.setText("");
            lblEmployeeName.setText(listViewSelectedEmployee);
          }
        });
  }

  @FXML
  private void onactionAddEmployee(ActionEvent event) throws IOException {
    lblEmployeeName.setText("");
    lblEmployeeRegistration.setText("");
    lvEmployees.getSelectionModel().clearSelection();
    String enteredNewEmployeeName = tfEmployeeName.getText();
    if (!enteredNewEmployeeName.isEmpty()) {
      emp = new Employee(enteredNewEmployeeName);
      emp.writeNewEmployee();
      employees.addNewEmployee(emp);
      employees.sortEmployeeList();
      lvEmployees.getItems().clear();
      for (Employee e : employees.getEmployees()) {
        lvEmployees.getItems().add(e.getEmployeeName());
      }
      lblMessages.setText(Settings.MSG_NEW_EMPLOYEE_ADDED);
      tfEmployeeName.setText("");
    } else {
      lblMessages.setText(Settings.ERR_ENTER_EMPLOYEE_NAME);
    }
  }

  @FXML
  private void onactionClose(ActionEvent event) {
    System.exit(0);
  }

  @FXML
  private void onactionGenerateReport(ActionEvent event) throws IOException {
    lblEmployeeName.setText("");
    lblEmployeeRegistration.setText("");
    lvEmployees.getSelectionModel().clearSelection();
    arrivals = new ArrivalList();
    arrivals.readArrivals();
    arrivals.sortForReport();
    arrivals.writeReport();
    lblMessages.setText(Settings.MSG_REPORT_GENERATED);
  }

  @FXML
  private void onactionRegisterArrival(ActionEvent event) throws IOException {
    if ((listViewSelectedEmployee != null) && !listViewSelectedEmployee.isEmpty()) {
      lblMessages.setText("");
      emp = new Employee(listViewSelectedEmployee);
      emp.recordArrival(listViewSelectedEmployee);
      lblEmployeeRegistration.setText(Settings.MSG_EMPLOYEE_ARRIVAL_RECORDED);
    }
  }

  @FXML
  private void onactionRemoveEmployee(ActionEvent event) throws IOException {
    lblEmployeeName.setText("");
    lblEmployeeRegistration.setText("");
    lvEmployees.getSelectionModel().clearSelection();
    String enteredEmployeeName = tfEmployeeName.getText();
    if (!enteredEmployeeName.isEmpty()) {
      boolean employeeFound = employees.removeExistingEmployee(enteredEmployeeName);
      if (employeeFound) {
        employees.writeAllEmployees();
        employees.sortEmployeeList();
        lvEmployees.getItems().clear();
        for (Employee e : employees.getEmployees()) {
          lvEmployees.getItems().add(e.getEmployeeName());
        }
        lblMessages.setText(Settings.MSG_EXISTING_EMPLOYEE_REMOVED);
        tfEmployeeName.setText("");
      } else {
        lblMessages.setText(Settings.MSG_EMPLOYEE_NOT_FOUND);
        tfEmployeeName.setText("");
      }
    } else {
      lblMessages.setText(Settings.ERR_ENTER_EMPLOYEE_NAME);
      tfEmployeeName.setText("");
    }
  }
}