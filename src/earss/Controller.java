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
 * Interface controller
 */
public class Controller implements Initializable {

    Employee employee;
    Registrar arrivalsAdmin;
    Collective employeesAdmin;
    String listViewSelectedEmployee;
    @FXML
    Button btnClose = new Button();
    @FXML
    Button btnAddEmployee = new Button();
    @FXML
    Button btnRemoveEmployee = new Button();
    @FXML
    Button btnGenerateReport = new Button();
    @FXML
    Button btnRegisterArrival = new Button();
    @FXML
    Label lblMessages = new Label();
    @FXML
    Label lblEmployeeName = new Label();
    @FXML
    Label lblEmployeeRegistration = new Label();
    @FXML
    TextField tfEmployeeName = new TextField();
    @FXML
    ListView<String> lvEmployees = new ListView<String>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        employeesAdmin = new Collective();
        try {
            employeesAdmin.readEmployees();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        employeesAdmin.sortEmployeeList();
        for (Employee e : employeesAdmin.getEmployees()) {
            lvEmployees.getItems().add(e.getEmployeeName());
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
    private void onActionAddEmployee(ActionEvent event) throws IOException {
        lblEmployeeName.setText("");
        lblEmployeeRegistration.setText("");
        lvEmployees.getSelectionModel().clearSelection();
        String newEmployeeName = tfEmployeeName.getText();
        if (!newEmployeeName.isEmpty()) {
            employee = new Employee(newEmployeeName);
            employee.writeNewEmployee();
            employeesAdmin.addNewEmployee(employee);
            employeesAdmin.sortEmployeeList();
            lvEmployees.getItems().clear();
            for (Employee e : employeesAdmin.getEmployees()) {
                lvEmployees.getItems().add(e.getEmployeeName());
            }
            lblMessages.setText(Settings.MSG_NEW_EMPLOYEE_ADDED);
            tfEmployeeName.setText("");
        } else {
            lblMessages.setText(Settings.ERR_ENTER_EMPLOYEE_NAME);
        }
    }

    @FXML
    private void onActionClose(ActionEvent event) {
        this.shutdown();
    }

    public void shutdown() {
        System.out.println("EARS has finished.");
        System.exit(0);
    }

    @FXML
    private void onActionGenerateReport(ActionEvent event) throws IOException {
        lblEmployeeName.setText("");
        lblEmployeeRegistration.setText("");
        lvEmployees.getSelectionModel().clearSelection();
        arrivalsAdmin = new Registrar();
        arrivalsAdmin.readArrivals();
        arrivalsAdmin.sortForReport();
        arrivalsAdmin.writeReport();
        lblMessages.setText(Settings.MSG_REPORT_GENERATED);
    }

    @FXML
    private void onActionRegisterArrival(ActionEvent event) throws IOException {
        if ((listViewSelectedEmployee != null) && !listViewSelectedEmployee.isEmpty()) {
            lblMessages.setText("");
            employee = new Employee(listViewSelectedEmployee);
            employee.recordArrival(listViewSelectedEmployee);
            lblEmployeeRegistration.setText(Settings.MSG_EMPLOYEE_ARRIVAL_RECORDED);
        }
    }

    @FXML
    private void onActionRemoveEmployee(ActionEvent event) throws IOException {
        lblEmployeeName.setText("");
        lblEmployeeRegistration.setText("");
        lvEmployees.getSelectionModel().clearSelection();
        String enteredEmployeeName = tfEmployeeName.getText();
        if (!enteredEmployeeName.isEmpty()) {
            boolean employeeFound = employeesAdmin.removeExistingEmployee(enteredEmployeeName);
            if (employeeFound) {
                employeesAdmin.writeAllEmployees();
                employeesAdmin.sortEmployeeList();
                lvEmployees.getItems().clear();
                for (Employee e : employeesAdmin.getEmployees()) {
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
