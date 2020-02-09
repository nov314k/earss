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

import java.nio.file.FileSystems;

/**
 * User-configurable settings, such as:
 * - File locations
 * - Formats, regex strings
 * - Messages (information and error messages)
 */
public class Settings {
    static final String PATH_SEPARATOR = FileSystems.getDefault().getSeparator();
    static final String APPLICATION_FORM_WINDOW_TITLE = "Employee Attendance Registration System (EARS)";
    static final String ARRIVAL_DATE_TIME_FORMAT = "yyyy.MM.dd HH:mm + ";
    static final String RECORDS_FOLDER_PATH = "records";
    static final String ARRIVAL_RECORDS_FILE_PATH = RECORDS_FOLDER_PATH + PATH_SEPARATOR + "arrivals.txt";
    static final String ARRIVAL_REGEX_STRING = " \\+ ";
    static final String ARRIVAL_REPORT_FILE_PATH = RECORDS_FOLDER_PATH + PATH_SEPARATOR + "report.txt";
    static final String CHARACTER_ENCODING = "UTF-8";
    static final String EMPLOYEE_NAMES_FILE_PATH = RECORDS_FOLDER_PATH + PATH_SEPARATOR + "employees.txt";
    static final String ERR_ENTER_EMPLOYEE_NAME = "ERROR: Please enter employee name";
    static final String MSG_EMPLOYEE_ARRIVAL_RECORDED = "Employee arrival has been recorded";
    static final String MSG_EMPLOYEE_NOT_FOUND = "Entered employee name is not on the list";
    static final String MSG_EXISTING_EMPLOYEE_REMOVED = "Existing employee has been removed";
    static final String MSG_NEW_EMPLOYEE_ADDED = "New employee has been added";
    static final String MSG_REPORT_GENERATED = "Attendance report has been generated";
}