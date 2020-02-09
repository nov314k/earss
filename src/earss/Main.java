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
import java.nio.file.Files;
import java.nio.file.Paths;
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
            System.exit(0);
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

    public void createSampleRecords() {

    }
}
