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

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardOpenOption.CREATE;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Application entry point.
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        if (!existRecords()) {
            this.createSampleRecords();
        }
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("Interface.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();
        Scene scene = new Scene(root);
        String css = this.getClass().getResource("earss.css").toExternalForm();
        scene.getStylesheets().add(css);
        primaryStage.setTitle(Settings.APPLICATION_FORM_WINDOW_TITLE);
        primaryStage.setScene(scene);
        primaryStage.setOnHidden(e -> controller.shutdown());
        primaryStage.show();
    }

    public boolean existRecords() {
        if (Files.isDirectory(Paths.get(Settings.RECORDS_FOLDER_PATH))
                && Files.isRegularFile(Paths.get(
                        Settings.EMPLOYEE_NAMES_FILE_PATH))
                && Files.isRegularFile(Paths.get(
                        Settings.ARRIVAL_RECORDS_FILE_PATH))
                && Files.isRegularFile(Paths.get(
                        Settings.ARRIVAL_REPORT_FILE_PATH))) {
            return true;
        }
        return false;
    }

    public void createSampleRecords() throws IOException {
        Path records_folder = Paths.get(Settings.RECORDS_FOLDER_PATH);
        if (!Files.isDirectory(records_folder)) {
            try {
                Files.createDirectories(records_folder);
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
        Path employees_file = Paths.get(Settings.EMPLOYEE_NAMES_FILE_PATH);
        if (!Files.isRegularFile(employees_file)) {
            OutputStream output = null;
            try {
                output = new BufferedOutputStream(
                        Files.newOutputStream(employees_file, CREATE));
                for (String s : Settings.SAMPLE_EMPLOYEES) {
                    s += Settings.NEW_LINE;
                    output.write(s.getBytes());
                }

            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName())
                        .log(Level.SEVERE, null, ex);
            } finally {
                output.flush();
                output.close();
            }
        }
        Path arrivals_file = Paths.get(Settings.ARRIVAL_RECORDS_FILE_PATH);
        if (!Files.isRegularFile(arrivals_file)) {
            try {
                Files.createFile(arrivals_file);
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
        Path report_file = Paths.get(Settings.ARRIVAL_REPORT_FILE_PATH);
        if (!Files.isRegularFile(report_file)) {
            try {
                Files.createFile(report_file);
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
    }
}
