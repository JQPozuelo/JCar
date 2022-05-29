package com.example.a5automocion.Clases;

import java.io.Serializable;

public class Usuario implements Serializable {
    String mail;

    public Usuario(String mail) {
        this.mail = mail;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "mail='" + mail + '\'' +
                '}';
    }
}
