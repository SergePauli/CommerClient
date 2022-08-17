package com.cbs.commerclient.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.client.Client;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.cbs.commerclient.model.Contract;
import com.cbs.commerclient.model.ResponseMsg;
import javax.ws.rs.core.Response;

/**
 * Jersey REST client generated for REST resource:Event Search Service
 * [categories]<br>
 * USAGE:
 * <pre>
 *        RestClient client = new RestClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author Serge Pauli
 * реализация взаимодействия с REST API основного приложения  
 */
public class RestClient {
    private WebTarget webTarget;
    private final Client client;
    private com.cbs.commerclient.model.ResponseMsg responseMsg;
    ObjectMapper mapper;
    private final static String MAIN_METHOD_PATH = "/getSelected";
    
    
    public RestClient() throws FileNotFoundException, IOException{      
        Properties props = new Properties();    
        props.load(new FileInputStream(new File("config/config.ini")));    
        String  baseURI = props.getProperty("BASE_URI");
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(baseURI).path(MAIN_METHOD_PATH);
        mapper = new ObjectMapper();      
    }

 

    /**
    * Подключение к web-сессии пользователя в браузере
    * @param user имя пользователя [REQUIRED]
    * @param pwd коммер-пароль [REQUIRED]   
    * @throws com.fasterxml.jackson.core.JsonProcessingException 
    */
    public void login(String user, String pwd) throws JsonProcessingException, javax.ws.rs.ProcessingException, IOException {
        responseMsg = null;
        webTarget = webTarget.queryParam("user", user).queryParam("pwd", pwd);
        String response = webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(String.class);
        responseMsg = mapper.readValue(response, ResponseMsg.class);
    }
  
    
    /**
    * Получение карточки контракта выбранного в web-приложении 
    * @param token - id открытой сессии в браузере
    * @return Contract - карточка контракта, выбранного в браузере
    * @throws com.fasterxml.jackson.core.JsonProcessingException
    */
    public Contract getSelected(String token) throws JsonProcessingException, javax.ws.rs.ProcessingException, IOException {
        webTarget = webTarget.queryParam("token", token);
        responseMsg = null;
        String response = webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(String.class); 
        if (response.contains("code")) {
            responseMsg = mapper.readValue(response, ResponseMsg.class);
            return new Contract();
        } else {
            return mapper.readValue(response, Contract.class);
        }
    }  
    
    /**
    * Сохранение изменений в карточке контракта  
    * @param token - id открытой сессии в браузере    
    * @param contract - карточка измененного контракта  
    * @throws com.fasterxml.jackson.core.JsonProcessingException
    */
    public void updateSelected(String token, Contract contract) throws JsonProcessingException, javax.ws.rs.ProcessingException, IOException{
        webTarget = webTarget.queryParam("token", token);
        String request = mapper.writeValueAsString(contract);
        responseMsg = null;
        Response response = webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).put(javax.ws.rs.client.Entity.json(request));
        if (response.getStatus() == 200) {      
            responseMsg = mapper.readValue(response.readEntity(String.class), ResponseMsg.class);
        } else {
            responseMsg = new ResponseMsg();
            responseMsg.setCode(response.getStatus());
            responseMsg.setData("Ошибка запроса к серверу: "+ response.getStatus());      
        }
    }  
   
    /**
    * Зактрыть соединение с API    
    */
    public void close() {
        client.close();
    }

    /**
    * Oтвет сервера на последний запрос     
    * @return ResponseMsg ответ сервера
    */
    public ResponseMsg getResponseMsg() {
        return responseMsg;
    }
    
}
