package it.sevenbits.hwspring.web.model.auth;

import org.junit.Assert;
import org.junit.Test;

public class TokenTest {

    @Test
    public void createTokenTest () {
        String stringToken = "token";
        Token token = new Token(stringToken);
        Assert.assertEquals(stringToken, token.getToken());
    };

}