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

/**
 * Models arrival data.
 * @author Novak Petrovic
 */
public class Arrival {
  private String employeeName;
  private String timeStamp;

  Arrival(final String employeeName, final String timeStamp) {
    this.employeeName = employeeName;
    this.timeStamp = timeStamp;
  }

  String getEmployeeName() {
    return this.employeeName;
  }

  void setEmployeeName(String employeeName) {
    this.employeeName = employeeName;
  }

  String getSortOnValue() {
    return this.employeeName + this.timeStamp;
  }

  String getTimeStamp() {
    return this.timeStamp;
  }

  void setTimeStamp(String timeStamp) {
    this.timeStamp = timeStamp;
  }
}