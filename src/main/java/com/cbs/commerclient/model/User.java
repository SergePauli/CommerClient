
package com.cbs.commerclient.model;

/**
 * данные сеанса пользователя
 * @author Serge Pauli
 */
public class User {
    private final String id;    
    public User(String id) { 
        this.id = id; 
    }
  
    public String getId() {  return id; }
    
    private String token;    
    private Contract contract = new Contract();

    public String getToken() {  return token; }
    public void setToken(String token) { this.token = token; }
  
    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }   
    
}
