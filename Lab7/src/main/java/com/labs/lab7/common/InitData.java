package com.labs.lab7.common;

import java.io.Serializable;

public class InitData implements Serializable {
    private final int port;
    private final String address;
    private final String login;
    private final String password;

    public InitData(int port, String address, String login, String password){
        this.port = port;
        this.address = address;
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public int getPort() {
        return port;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "InitData{" +
                "port=" + port +
                ", address='" + address + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
