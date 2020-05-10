package entity;

import org.springframework.stereotype.Component;


@Component
public class UserMail {

    private String firstName;
    private String lastName;
    private String emailAddress;
    private String subject;
    private String text;

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmailAddress() {
        return emailAddress;
    }
    public String getSubject() {
        return subject;
    }
    public String getText() { return text; }
    public void setSubject(String subject) {
        this.subject =  subject;
    }
    public void setText(String text) { this.text = text; }
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

}

