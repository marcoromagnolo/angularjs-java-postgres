package dmt.server.test.service;

import dmt.server.service.AccountService;
import dmt.server.service.exception.GenericException;
import dmt.server.service.exception.InternalException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Locale;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/appconfig.xml"})
public class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @Test
    public void testRegister() throws GenericException, InternalException {
        accountService.register(new Locale("IT"), "firstName", "lastName", "email@email.com", "test", "pass");
    }
}
