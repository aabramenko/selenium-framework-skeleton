package project.models;

public class Users {

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    public Users(final String username,
                 final String password,
                 final String firstName,
                 final String lastName) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}


