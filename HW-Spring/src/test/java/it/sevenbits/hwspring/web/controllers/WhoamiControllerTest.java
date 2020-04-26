package it.sevenbits.hwspring.web.controllers;

import it.sevenbits.hwspring.core.service.TasksService;
import it.sevenbits.hwspring.web.model.tasks.Pagination;
import it.sevenbits.hwspring.web.service.WhoamiService;
import junit.framework.TestCase;
import org.junit.Before;

import static org.mockito.Mockito.mock;

public class WhoamiControllerTest extends TestCase {
    private WhoamiController whoamiController;
    private WhoamiService whoamiService;

    @Before
    public void setup() {
        whoamiService = mock(WhoamiService.class);
        whoamiController = new WhoamiController(whoamiService);
    }

    public void testGet() {
    }
}