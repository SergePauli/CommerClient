
package com.cbs.commerclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ответ сервера
 * @author Serge Pauli
 */
public class ResponseMsg {
    @JsonProperty("code")
    private int code;
    public int getCode() { return code; }
    public void setCode(int code) { this.code = code; }
    
    @JsonProperty("data")
    public String data;
    public String getData() { return data; }
    public void setData(String data) { this.data = data; }
    
    public ResponseMsg() {}  

    public ResponseMsg(int code, String data) {
      this.code = code;
      this.data = data;
    }
    
  }
