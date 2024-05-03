package Najavnist.java.bot.DataBase;

import javax.persistence.*;

    @Entity
    @Table(name = "users_zel")//вказую таблицю
    public class Person {
        @Id
        @Column(name = "id")//стовбець в бд
        @GeneratedValue(strategy = GenerationType.IDENTITY)//генерує автоматично постгересом
        private long id;
        @Column(name = "check_surname")
        private boolean checkSurname;
        @Column(name = "users_name")
        private String userName;
        @Column(name = "check_number")
        private boolean checkNumber;
        @Column(name = "user_phone")
        private int userPhone;
        @Column(name = "user_tgname")
        private String userTgName;
        @Column(name = "user_presence")
        private String userPresence;
        @Column(name = "user_location")
        private String userLocation;
        @Column(name = "user_time")
        private String userTime;
        @Column(name = "check_keyboard")
        private int checkKeyboard;
        @Column(name = "check_start")
        private boolean checkStart;

        public Person(){}
        public Person(boolean checkSurname,String userName,boolean checkNumber,int userPhone,String userTgName,String userPresence,String userLocation,String userTime,int checkKeyboard,boolean checkStart){

            this.checkSurname=checkSurname;
            this.userName=userName;
            this.checkNumber=checkNumber;
            this.userPhone=userPhone;
            this.userTgName=userTgName;
            this.userPresence=userPresence;
            this.userLocation=userLocation;
            this.userTime=userTime;
            this.checkKeyboard=checkKeyboard;
            this.checkStart=checkStart;
        }
        public Person(boolean checkSurname,boolean checkNumber,String userTgName,boolean checkStart){

            this.checkSurname=checkSurname;
            this.checkNumber=checkNumber;
            this.userTgName=userTgName;
            this.checkStart=checkStart;
        }
        public String toString(){
            return this.userName+", "+this.userPhone;
        }


        public long getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean isCheckSurname() {
            return checkSurname;
        }

        public void setCheckSurname(boolean checkSurname) {
            this.checkSurname = checkSurname;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public boolean isCheckNumber() {
            return checkNumber;
        }

        public void setCheckNumber(boolean checkNumber) {
            this.checkNumber = checkNumber;
        }

        public int getUserPhone() {
            return userPhone;
        }

        public void setUserPhone(int userPhone) {
            this.userPhone = userPhone;
        }

        public String getUserTgName() {
            return userTgName;
        }

        public void setUserTgName(String userTgName) {
            this.userTgName = userTgName;
        }

        public String getUserPresence() {
            return userPresence;
        }

        public void setUserPresence(String userPresence) {
            this.userPresence = userPresence;
        }

        public String getUserLocation() {
            return userLocation;
        }

        public void setUserLocation(String userLocation) {
            this.userLocation = userLocation;
        }

        public String getUserTime() {
            return userTime;
        }

        public void setUserTime(String userTime) {
            this.userTime = userTime;
        }

        public int isCheckKeyboard() {
            return checkKeyboard;
        }

        public void setCheckKeyboard(int checkKeyboard) {
            this.checkKeyboard = checkKeyboard;
        }

        public boolean isCheckStart() {
            return checkStart;
        }

        public void setCheckStart(boolean checkStart) {
            this.checkStart = checkStart;
        }
    }