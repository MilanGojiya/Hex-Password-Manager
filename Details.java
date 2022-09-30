package com.NoGravity.HexPM;

public class Details {
    String utitle;
    String uname;
    String upass;
    String uweb;
    String ucomment;


    public Details(){}


    public Details(String utitle, String uname, String upass, String uweb, String ucomment ) {
        this.utitle = utitle;
        this.uname = uname;
        this.upass = upass;
        this.uweb = uweb;
        this.ucomment = ucomment;

    }

    public String getUtitle() {
        return utitle;
    }

    public void setUtitle(String utitle) {
        this.utitle = utitle;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUpass() {
        return upass;
    }

    public void setUpass(String upass) {
        this.upass = upass;
    }

    public String getUweb() {
        return uweb;
    }

    public void setUweb(String uweb) {
        this.uweb = uweb;
    }

    public String getUcomment() {
        return ucomment;
    }

    public void setUcomment(String ucomment) {this.ucomment = ucomment;}


}
