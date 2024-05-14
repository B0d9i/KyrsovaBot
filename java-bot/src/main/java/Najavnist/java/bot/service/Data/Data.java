package Najavnist.java.bot.service.Data;

public class Data implements InterfaceData {

    private boolean checkSurname=false;//для перевірки. прізвище чи рандомний текст
    private boolean checkNumber=false;//щоб сприймав тільки тоді коли потрібно
    private boolean checkLocation =false;//щоб сприймав тільки тоді коли потрібно
    private boolean checkReplacement =false;//перевірка чи міняв прізвище
    // (щоб коли прізвище мінялось не запитував номер толефону ще раз)
    private boolean checkStart=false;//перевірка чи раніше запускав
    private boolean checkForward=false;//перевірка чи пересилав повідомлення



    private String сhatId="";//записує шоб порівняти з попереднім id
    private String surname="";//прізвище
    private String number="";//номер телефону
    private String location="";//посилання на гугл карти
    private String present="";//наявний, за межами, відпустка, звільнення
    private String date="";//дата і час геолокації

    public void setChatId(String user_ChatId) {
        сhatId = user_ChatId;
    }

    public String getChatId() {
        return сhatId;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setCheckSurname(boolean checkSurname) {
        this.checkSurname = checkSurname;
    }

    public void setCheckNumber(boolean checkNumber) {
        this.checkNumber = checkNumber;
    }

    public void setCheckLocation(boolean checkLocation) {
        this.checkLocation = checkLocation;
    }

    public void setCheckReplacement(boolean checkReplacement) {
        this.checkReplacement = checkReplacement;
    }

    public void setCheckStart(boolean checkStart) {
        this.checkStart = checkStart;
    }

    public void setCheckForward(boolean checkForward) {
        this.checkForward = checkForward;
    }

    public boolean getCheckSurname() {
        return checkSurname;
    }

    public boolean getCheckNumber() {
        return checkNumber;
    }

    public boolean getCheckLocation() {
        return checkLocation;
    }

    public boolean getCheckReplacement() {
        return checkReplacement;
    }

    public boolean getCheckStart() {
        return checkStart;
    }

    public boolean getCheckForward() {
        return checkForward;
    }

    public void setPresent(String present) {//наявність
        this.present = present;
    }

    public void setDate(String date) {
        this.date = date;
    }



    public String getSurname() {
        return surname;
    }

    public String getNumber() {
        return number;
    }

    public String getLocation() {
        return location;
    }

    public String getPresent() {
        return present;
    }

    public String getDate() {
        return date;
    }


}
