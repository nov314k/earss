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
 * Arrivals data model
 */
public class Registrar {
    ArrayList<Arrival> arrivals;

    Registrar() {
        this.arrivals = new ArrayList<>();
    }
  
    /**
    * Reads arrival records from a text file.
    * @throws IOException Reading from a text file
    */
    public void readArrivals() throws IOException {
        String[] tokens;
        String s = null;
        BufferedReader br = null;
        File f = new File(Settings.ARRIVAL_RECORDS_FILE_NAME);
        try {
            br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(f), Settings.CHARACTER_ENCODING));
            while ((s = br.readLine()) != null) {
                tokens = s.split(Settings.ARRIVAL_REGEX_STRING);
                this.arrivals.add(new Arrival(tokens[1], tokens[0]));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            br.close();
        }
    }
  
    /**
    * Sorts arrival records on employee name. 
    */
    public void sortForReport() {
        Collections.sort(this.arrivals, (a1, a2) -> a2.getSortOnValue().compareTo(a1.getSortOnValue()));
    }
  
    /**
    * Writes attendance report to a text file.
    * @throws IOException Writing to a text file
    */
    public void writeReport() throws IOException {
        File f = new File(Settings.ARRIVAL_REPORT_FILE_NAME);
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(f, false), Settings.CHARACTER_ENCODING));
            for (Arrival a : this.arrivals) {
                bw.write(a.getEmployeeName());
                bw.write(": ");
                bw.write(a.getTimeStamp());
                bw.write('\n');
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            bw.close();
        }
    }
}