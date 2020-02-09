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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;

import java.util.Date;

/**
 * Models employee data.
 */
public class Employee {
    private String employeeName;

    public Employee(final String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeName() {
        return employeeName;
    }
  
    /**
    * Writes employee arrival date and time to a text file.
    * @param employeeName Name of the employee
    * @throws IOException Writing to a text file
    */
    public void recordArrival(String employeeName) throws IOException {
        File f = new File(Settings.ARRIVAL_RECORDS_FILE_NAME);
        BufferedWriter bw = null;
        String timest = new SimpleDateFormat(Settings.ARRIVAL_DATE_TIME_FORMAT).format(new Date());
        Arrival arrival = new Arrival(employeeName, timest);
        try {
            OutputStreamWriter osw = new OutputStreamWriter(
                    new FileOutputStream(f, true), 
                    Settings.CHARACTER_ENCODING);
            bw = new BufferedWriter(osw);
            bw.write(arrival.getTimeStamp());
            bw.write(arrival.getEmployeeName());
            bw.write('\n');
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            bw.close();
        }
    }

    public void setEmployeeName(final String employeeName) {
        this.employeeName = employeeName;
    }

    @Override
    public String toString() {
        return employeeName;
    }

    /**
    * Writes the name of a new employee to text file.
    * @throws IOException Writing to a text file
    */
    public void writeNewEmployee() throws IOException {
        File f = new File(Settings.EMPLOYEE_NAMES_FILE_NAME);
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(
                    new OutputStreamWriter(
                        new FileOutputStream(f, true),
                        Settings.CHARACTER_ENCODING));
            bw.write(this.getEmployeeName());
            bw.write('\n');
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            bw.close();
        }
    }
}