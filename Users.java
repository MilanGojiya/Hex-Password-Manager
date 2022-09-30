package com.NoGravity.HexPM;

public class Users {

    String utitle;
    String uname;
    String upass;
    String uweb;
    String ucomment;



    public Users(String utitle, String uname, String upass, String uweb, String ucomment) {
        this.utitle = utitle;
        this.uname = uname;
        this.upass = upass;
        this.uweb = uweb;
        this.ucomment = ucomment;


    }

    public String getUtitle() {
        return utitle;
    }

    public String getUname() {
        return uname;
    }

    public String getUpass() {
        return upass;
    }

    public String getUweb() {
        return uweb;
    }

    public String getUcomment() {return ucomment;}




}
