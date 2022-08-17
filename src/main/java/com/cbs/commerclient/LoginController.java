package com.cbs.commerclient;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import com.cbs.commerclient.service.RestClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.cbs.commerclient.model.User;

/**
 * функционал формы входа
 * @author serge pauli
 */
public class LoginController  extends AnchorPane implements Initializable {
    @FXML
    TextField userId;
    @FXML
    PasswordField password;    
    @FXML
    Label errorMessage;
    
    
    @FXML
    public void switchToContract() throws IOException {
        if (App.restClient == null) return;        
        if (userLogging())
        App.setRoot("contract");        
    }
    
    private boolean userLogging() {       
      try {          
        App.restClient.login(userId.getText(), password.getText());
        if (App.restClient.getResponseMsg().getCode() == 1) {
            App.loggedUser = new User(userId.getText());
            App.loggedUser.setToken(App.restClient.getResponseMsg().getData());            
            return true;
        } else {
            errorMessage.setText(App.restClient.getResponseMsg().getData());
            return false;
        }
        
      } catch (JsonProcessingException ex) {
        errorMessage.setText(ex.getLocalizedMessage());
        return false;
      } catch (javax.ws.rs.ProcessingException ex) {
        errorMessage.setText("Ошибка: Сервер базы не отвечает!");
        return false;
      } catch (IOException ex) {
        errorMessage.setText("Ошибка: Сервер вернул ошибку!");
        return false;
      }         
    }

    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        errorMessage.setText("");
        try {
            App.restClient = new RestClient();
        } catch (IOException ex) {
            errorMessage.setText("Файл с настройками испорчен или отсутствует");
        }        
        userId.setPromptText("comm");
        password.setPromptText("comm");
    }
}
