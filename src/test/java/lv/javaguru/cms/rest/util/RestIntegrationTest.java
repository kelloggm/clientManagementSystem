package lv.javaguru.cms.rest.util;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import org.apache.http.HttpHost;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.web.client.RestTemplate;

import java.sql.SQLException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class
})
public abstract class RestIntegrationTest {

    @Autowired
    private ApplicationContext applicationContext;

    @LocalServerPort
    int randomServerPort;

    protected static final String ADMIN_LOGIN = "admin";
    protected static final String ADMIN_PASSWORD = "password";

    protected static final String CLIENT_MANAGER_LOGIN = "client_manager_login";
    protected static final String CLIENT_MANAGER_PASSWORD = "password";

    protected static final String COURSE_MANAGER_LOGIN = "course_manager_login";
    protected static final String COURSE_MANAGER_PASSWORD = "password";

    protected static final String BILL_MANAGER_LOGIN = "bill_manager";
    protected static final String BILL_MANAGER_PASSWORD = "password";


    @Before
    public void setUp() throws SQLException {
        DbTestUtil.resetAutoIncrementColumns(applicationContext);
    }

    protected RestTemplate getRestTemplate(String username, String password) {
        HttpHost host = new HttpHost("localhost", randomServerPort, "http");
        RestTemplate restTemplate = new RestTemplate(
                new HttpComponentsClientHttpRequestFactoryBasicAuth(host)
        );
        restTemplate.getInterceptors().add(
                new BasicAuthorizationInterceptor(username, password));

        return restTemplate;
    }

    protected String baseUrl() {
        return "http://localhost:" + randomServerPort;
    }

}
