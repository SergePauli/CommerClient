
package com.cbs.commerclient.model;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Карточка открытого в браузере контракта 
 * @author Serge Pauli
 */
public class Contract {
  
  @JsonProperty("id")
  private String id;
  public String getId() { return id; }
  public void setId(String id) { this.id = id; }
  
  @JsonProperty("number")
  private String number;
  public String getNumber() { return number; }
  public void setNumber(String number) { this.number = number; }
  
  @JsonProperty("contragent")
  private String contragent;
  public String getContragent() {  return contragent; }
  public void setContragent(String contragent) { this.contragent = contragent; }  
  
  @JsonProperty("docFile")
  private String docFile;
  public String getDocFile() { return docFile; }
  public void setDocFile(String docfile) { this.docFile = docfile; }
  
  @JsonProperty("scanFile")
  private String scanFile;
  public String getScanFile() {  return scanFile; }
  public void setScanFile(String scanFile) { this.scanFile = scanFile;  }
  
  @JsonProperty("protocol")
  private String protocol;
  public String getProtocol() { return protocol; }
  public void setProtocol(String protocol) { this.protocol = protocol; }
  
  @JsonProperty("archive")
  private String archive;
  public String getArchive() {  return archive; }
  public void setArchive(String archive) { this.archive = archive; }

  public Contract() {
    
  }  
    
}
