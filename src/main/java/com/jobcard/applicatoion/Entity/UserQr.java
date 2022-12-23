package com.jobcard.applicatoion.Entity;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserQr {
 
 
 
    private String firstName;
    private String lastName;
     
    private String cin;
    private String Profession;
    private Date birthdayDate;
  
  
  
    
    
}
