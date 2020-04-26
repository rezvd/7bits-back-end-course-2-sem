package it.sevenbits.hwspring.web.model.auth;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

public class SignInTest {

    @Test
    public void creatingSignIn() {
        String username = "user";
        String password = "userpassword";
        SignIn signIn = new SignIn(username, password);

        Assert.assertEquals(username, signIn.getUsername());
        Assert.assertEquals(password, signIn.getPassword());
    }
}