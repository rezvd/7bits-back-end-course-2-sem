package it.sevenbits.hwspring.web.model.auth;

import org.junit.Assert;
import org.junit.Test;

public class SignUpTest {

    @Test
    public void creatingSignIn() {
        String username = "user";
        String password = "userpassword";
        SignUp signUp = new SignUp(username, password);

        Assert.assertEquals(username, signUp.getUsername());
        Assert.assertEquals(password, signUp.getPassword());
    }
}