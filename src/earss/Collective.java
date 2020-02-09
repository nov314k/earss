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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;

/**
 * <code>ArrayList</code> of <code>Employee</code>s, with methods to add/remove,
 * read/write, etc.
 */
public class Collective {

    private ArrayList<Employee> employees;

    Collective() {
        this.employees = new ArrayList<>();
    }

    public void addNewEmployee(Employee employee) {
        this.employees.add(employee);
    }

    public ArrayList<Employee> getEmployees() {
        return this.employees;
    }

    /**
     * Reads all employee names from a text file.
     *
     * @throws IOException Reading from a text file
     */
    public void readEmployees() throws IOException {
        String s;
        BufferedReader br = null;
        File f = new File(Settings.EMPLOYEE_NAMES_FILE_PATH);
        try {
            InputStreamReader isr = new InputStreamReader(
                    new FileInputStream(f),
                    Settings.CHARACTER_ENCODING);
            br = new BufferedReader(isr);
            while ((s = br.readLine()) != null) {
                this.employees.add(new Employee(s));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            br.close();
        }
    }

    /**
     * Searches for an employee (by name), and, if found, removes it.
     *
     * @param employeeNameToRemove Employee name to search and remove
     * @return A boolean indicating whether the name was found or not
     */
    public boolean removeExistingEmployee(String employeeNameToRemove) {
        int aux = 0;
        boolean employeeFound = false;
        for (Employee e : employees) {
            if (e.getEmployeeName().equals(employeeNameToRemove)) {
                employees.remove(aux);
                employeeFound = true;
                break;
            } else {
                ++aux;
            }
        }
        return employeeFound;
    }

    /**
     * Sorts employees by name.
     */
    public void sortEmployeeList() {
        Collections.sort(
                this.employees,
                (e1, e2) -> e1.getEmployeeName().compareTo(e2.getEmployeeName()));
    }

    /**
     * Writes all employees to a text file. This method is used each time when a
     * new employee is added, to store their name, and this is perhaps an
     * overkill. An alternative is to only insert the new employee name into the
     * text file.
     *
     * @throws IOException Writing to a text file
     */
    public void writeAllEmployees() throws IOException {
        File f = new File(Settings.EMPLOYEE_NAMES_FILE_PATH);
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(f, false),
                            Settings.CHARACTER_ENCODING));
            for (Employee e : this.employees) {
                bw.write(e.getEmployeeName());
                bw.write('\n');
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            bw.close();
        }
    }
}
