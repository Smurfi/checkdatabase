/*
 * Copyright (C) 2019 Michael Kroll
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
package checkdatabase;

import checkdatabase.db.OracleConnect;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 *
 * @author mkroll
 */
public class MainController implements Initializable {

    @FXML
    TextField hostTextField;
    @FXML
    TextField sidTextField;
    @FXML
    TextField portTextField;
    @FXML
    TextField userNameTextField;
    @FXML
    PasswordField passwordPasswordField;
    @FXML
    TextField statusTextField;
    @FXML
    Button testConnectionButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Nothing todo
    }

    /**
     * Exit application
     *
     * @param event
     */
    @FXML
    protected void exitButtonAction(ActionEvent event) {
        Platform.exit();
    }

    /**
     * Test databse connection
     *
     * @param event
     */
    @FXML
    protected void testConnectionButtonAction(ActionEvent event) {
        try {
            statusTextField.setText("");
            OracleConnect.connectDB(hostTextField.getText(), portTextField.getText(), sidTextField.getText(), userNameTextField.getText(), passwordPasswordField.getText());
            statusTextField.setText(OracleConnect.checkConnection());
        } catch (ClassNotFoundException | SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("JDBC-Error");
            alert.setHeaderText("JDBC-Error");
            alert.setContentText("" + ex);
            alert.showAndWait();
        }
    }
}
