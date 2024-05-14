package Najavnist.java.bot.service.Data;

import org.springframework.core.env.Environment;

public interface InterfaceData {


    public void setSurname(String surname);
    public void setNumber(String number);
    public void setLocation(String location);
    public String getSurname();
    public String getNumber();
    public String getLocation();

}
