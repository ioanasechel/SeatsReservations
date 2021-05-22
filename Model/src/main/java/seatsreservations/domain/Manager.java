package seatsreservations.domain;

import java.io.Serializable;

public class Manager implements Serializable {
    private String username;
    private String password;
    private String name;

    public Manager(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
    }

    public Manager() {

    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public void setUsername(String username) { this.username = username;}

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Manager{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
