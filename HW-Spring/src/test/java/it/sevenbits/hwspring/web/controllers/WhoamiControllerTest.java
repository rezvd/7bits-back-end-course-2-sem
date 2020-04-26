package it.sevenbits.hwspring.web.controllers;

import it.sevenbits.hwspring.core.model.User;
import it.sevenbits.hwspring.web.service.WhoamiService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class WhoamiControllerTest {
    private WhoamiController whoamiController;
    private WhoamiService whoamiService;

    @Before
    public void setup() {
        whoamiService = mock(WhoamiService.class);
        whoamiController = new WhoamiController(whoamiService);
    }

    @Test
    public void testGet() {
        User user = mock(User.class);
        when(whoamiService.getUserFromContext()).thenReturn(user);
        ResponseEntity response = whoamiController.get();
        verify(whoamiService, times(1)).getUserFromContext();
        Assert.assertSame(user, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}