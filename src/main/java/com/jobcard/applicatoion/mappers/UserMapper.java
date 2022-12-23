package com.jobcard.applicatoion.mappers;

import com.jobcard.applicatoion.Entity.User;
import com.jobcard.applicatoion.Entity.UserQr;

public class UserMapper {

    public static UserQr userToUserQR(User acc) {
        UserQr accp = new UserQr();

        accp.setFirstName(acc.getFirstName());
        accp.setLastName(acc.getLastName());
        accp.setCin(acc.getCin());
        accp.setBirthdayDate(acc.getBirthdayDate());

        return accp;
    }

}
