
package com.cbs.commerclient;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.animation.FadeTransition;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;



/**
 * функционал карточки контракта
 * @author Serge Pauli
 */
public class ContractController  extends AnchorPane implements Initializable {
    
    //поля FXML-формы 
    
    @FXML
    private Label user;
    @FXML
    private Label contractInfo;  
    @FXML
    private TextArea docFile;  
    @FXML
    private TextArea scanFile;  
    @FXML
    private TextArea protocol;  
    @FXML
    private TextArea archive;  
    @FXML 
    private Label success;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        user.setText(App.loggedUser.getId());
        try { 
            getContract();
        } catch (IOException ex) {
            success.setText("Ошибка: Сервер базы не отвечает!!!");
            animateMessage();
            success.setOpacity(0);
        }      
              
    }
    
    @FXML
    public void processLogout() throws IOException {            
        App.loggedUser = null;         
        App.restClient.close();     
        App.setRoot("login");        
    }
    
    
    private void animateMessage() {
        FadeTransition ft = new FadeTransition(Duration.millis(1000), success);
        ft.setFromValue(0.0);
        ft.setToValue(1);
        ft.play();
    }
    
    /**
    * получение карточки контракта, открытого в браузере    
    * обработка клика на ВЫБОР
    * @throws java.io.IOException
    */
    public void getContract() throws IOException{        
      try {
            App.loggedUser.setContract(App.restClient.getSelected(App.loggedUser.getToken()));
            docFile.setText(App.loggedUser.getContract().getDocFile());
            scanFile.setText(App.loggedUser.getContract().getScanFile());
            protocol.setText(App.loggedUser.getContract().getProtocol());
            archive.setText(App.loggedUser.getContract().getArchive());
            contractInfo.setText(App.loggedUser.getContract().getNumber()
            +" "+App.loggedUser.getContract().getContragent());
            if (App.restClient.getResponseMsg() == null) {
                success.setOpacity(0);
            } else {
                success.setText(App.restClient.getResponseMsg().getData());
                animateMessage();
            }        
      } catch (JsonProcessingException ex) {
            success.setText(ex.getLocalizedMessage());
            animateMessage();        
      } catch (javax.ws.rs.ProcessingException ex) {
            success.setText("Ошибка: Сервер базы не отвечает!!!");
            animateMessage();
      } 
    }
    
    /**
    * выбор файла для прикрепления к контракту из сетевой папки     * 
    */
    private String choseFile(String[] descriptions, String[] extentions) throws FileNotFoundException, IOException {    
        FileChooser fileChooser = new FileChooser();
        int i = 0;
        for (String extention : extentions) {
            FileChooser.ExtensionFilter extFilter = 
                new FileChooser.ExtensionFilter(descriptions[i++], extention);
            fileChooser.getExtensionFilters().add(extFilter);
        }
        Properties props = new Properties();    
        props.load(new FileInputStream(new File("config/config.ini")));    
        String  SHARED_DIR = props.getProperty("SHARED_DIR");
        File dir = new File(SHARED_DIR);
        if (dir.exists()) fileChooser.setInitialDirectory(dir);
        File file = fileChooser.showOpenDialog(App.aStage); 
        if (file!=null) return file.getAbsolutePath();
        else return "";
    }
    
    /**
    * открытие прикрепленного к контракту файла в приложении по умолчанию     * 
    */
    private boolean execFile(File file) {        
     try {
        if (OSDetector.isWindows()) {
            Runtime.getRuntime().exec(new String[]{"rundll32", "url.dll,FileProtocolHandler",
            file.getAbsolutePath()});            
        } else if (OSDetector.isLinux() || OSDetector.isMac()) {
            System.out.println("OSDetector.isLinux() "+OSDetector.isLinux()+file.getAbsolutePath());
            Runtime.getRuntime().exec(new String[]{"xdg-open", file.getAbsolutePath()});            
        } else {
            success.setText("Ошибка: OС не поддерживает запуск файла");
            animateMessage();            
        } 
        return true;
      } catch (Exception e) {
        success.setText("Ошибка: "+e.getLocalizedMessage());
        animateMessage();
        return false;
      }
    }
    
    /**
    * открытие, привязаного к контракту файла    * 
    */
    private void openFile(String fileName){
        if (fileName!=null ) {
            File file = new File(fileName);
            if (file.exists()) {
                if (execFile(file)) success.setOpacity(0.0);                
            } else {
                success.setText("Ошибка: Файл не найден");
                animateMessage();        
            }    
        } else {
           success.setText("Ошибка: Файл отсутствует");
           animateMessage();  
        } 
    }  
    
    
    /**
    * обработка клика на кнопке файла контракта     * 
     * @param event
     * @throws java.io.IOException
    */
    public void processFileDoc(ActionEvent event) throws IOException {
        String[] extentions = {"*.doc*"};
        String[] descriptions =  {"Doc files (*.doc)"};
        String fileName = choseFile(descriptions, extentions);    
        if (!"".equals(fileName)) {
            docFile.setText(fileName);      
            success.setText("Внимание, есть изменения: Договор");
            animateMessage();   
        }
    }
  
    /**
    * обработка клика на кнопке скана контракта     * 
     * @param event
     * @throws java.io.IOException
    */
    public void processFilePDF(ActionEvent event) throws IOException{
        String[] extentions = {"*.pdf","*.jpg"};
        String[] descriptions = {"PDF files ","JPEG files "};
        String fileName = choseFile(descriptions, extentions);    
        if (!"".equals(fileName)) {
            scanFile.setText(fileName);      
            success.setText("Внимание, есть изменения: Скан");
        animateMessage(); 
        }
    }
  
    /**
    * обработка клика на кнопке протокола контракта     * 
     * @param event
     * @throws java.io.IOException
    */
    public void processProtocol(ActionEvent event) throws IOException{
        String[] extentions = {"*.xls*", "*.doc*"};
        String[] descriptions = {"*.xls*", "*.doc*"};
        String fileName = choseFile(descriptions, extentions);
        if (!"".equals(fileName)) {
            protocol.setText(fileName);      
            success.setText("Внимание, есть изменения: Протокол");
            animateMessage(); 
        }
    }
  
    /**
    * обработка клика на кнопке архива контракта     * 
     * @param event
     * @throws java.io.IOException
    */
    public void processArhive(ActionEvent event) throws IOException{
        String[] extentions = {"*.xls*", "*.doc*","*.zip","*.rar","*.7zp"};
        String[] descriptions = {"Excel files", "Word files","Zip files","Rar files","*.7zp files"};
        String fileName = choseFile(descriptions, extentions);
        if (!"".equals(fileName)) {
            archive.setText(fileName);      
            success.setText("Внимание, есть изменения: Приложение");
            animateMessage(); 
        }
    }
    
    
    // обработка кликов на метках, рядом с именами файлов (открытие этих файлов через ОС) 
    public void openDocFile(){ openFile(docFile.getText()); }
    public void openScanFile(){ openFile(scanFile.getText()); }
    public void openProtocol(){ openFile(protocol.getText()); }
    public void openArchive(){ openFile(archive.getText()); }
    
    
    
    
    /**
    * обработка клика Сброс - отмена изменений в контракте     * 
     * @param event     
    */
    public void resetContract(ActionEvent event) {
        if (App.loggedUser.getContract() == null) return;
        docFile.setText(App.loggedUser.getContract().getDocFile());
        scanFile.setText(App.loggedUser.getContract().getScanFile());
        protocol.setText(App.loggedUser.getContract().getProtocol());
        archive.setText(App.loggedUser.getContract().getArchive());
        success.setOpacity(0.0);
    }
    
    
    /**
    * обработка клика ВНЕСТИ - сохранение изменений в контракте     * 
     * @param event     
     * @throws java.io.IOException     
    */
    public void saveContract(ActionEvent event) throws IOException {
        if (App.loggedUser.getContract() == null) return;    
        App.loggedUser.getContract().setDocFile(docFile.getText());
        App.loggedUser.getContract().setScanFile(scanFile.getText());
        App.loggedUser.getContract().setProtocol(protocol.getText());
        App.loggedUser.getContract().setArchive(archive.getText());            
     try {
        App.restClient.updateSelected(App.loggedUser.getToken(),App.loggedUser.getContract());
        if (App.restClient.getResponseMsg().getCode() == 1) {
            success.setText("Текущие изменения успешно внесены в контракт");
            animateMessage();
        } else {
            success.setText(App.restClient.getResponseMsg().getData());
            animateMessage();
        }        
     } catch (JsonProcessingException ex) {
        success.setText(ex.getLocalizedMessage());
        animateMessage();        
     } catch (javax.ws.rs.ProcessingException ex) {
        success.setText("Ошибка: Сервер базы не отвечает!!!");
        animateMessage(); 
     }  
    }
  
}
