package BIF.SWE1.unittests;

import BIF.SWE1.UEB7;
import BIF.SWE1.httpUtils.Request;
import BIF.SWE1.httpUtils.ResponseFactory;
import BIF.SWE1.interfaces.IPlugin;
import BIF.SWE1.interfaces.IRequest;
import BIF.SWE1.interfaces.IResponse;
import BIF.SWE1.pluginHelper.Temperature;
import org.junit.*;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class UEB7Test extends AbstractTestFixture<UEB7> {
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Override
    protected UEB7 createInstance() {
        return new UEB7();
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void HelloWorld() throws Exception {
        UEB7 ueb = createInstance();
        ueb.helloWorld();
    }

    /*
     * Testing ResponseFactory
     */

    @Test
    public void factory_returns_response_2_parameters() {
        UEB7 ueb = new UEB7();
        String contentType = "text/html";
        String content = "Content";
        IResponse resp = ResponseFactory.create(contentType, content);

        assertNotNull(resp);
        assertEquals(resp.getContentType(), contentType);
        assertEquals(resp.getContentLength(), content.length());
    }

    @Test
    public void factory_returns_response_2_parameters_bytes() {
        UEB7 ueb = new UEB7();
        String contentType = "text/html";
        String string = "content";
        byte[] content = string.getBytes();
        IResponse resp = ResponseFactory.create(contentType, content);

        assertNotNull(resp);
        assertEquals(resp.getContentType(), contentType);
        assertEquals(resp.getContentLength(), content.length);
    }

    @Test
    public void factory_returns_response_3_parameters() {
        UEB7 ueb = new UEB7();
        String string = "content";
        String contentType = "text/html";
        int statusCode = 200;
        byte[] content = string.getBytes();
        IResponse resp = ResponseFactory.create(statusCode, contentType, string);
        assertNotNull(resp);
        assertEquals(resp.getContentType(), contentType);
        assertEquals(resp.getContentLength(), content.length);
        assertEquals(resp.getStatusCode(), statusCode);
    }

    @Test
    public void factory_returns_response_4_parameters() {
        UEB7 ueb = new UEB7();
        String string = "content";
        String contentType = "text/html";
        String server = "BIF.Server";
        int statusCode = 200;
        byte[] content = string.getBytes();
        IResponse resp = ResponseFactory.create(statusCode, server,contentType, string);
        assertNotNull(resp);
        assertEquals(resp.getContentType(), contentType);
        assertEquals(resp.getContentLength(), content.length);
        assertEquals(resp.getStatusCode(), statusCode);
        assertEquals(resp.getServerHeader(), server);
    }

    /*
    * Temperature
     */
    @Test
    public void hello_temperature() {
        UEB7 ueb = new UEB7();
        Temperature temp = ueb.getTemperature(-3.4123, 1202, "2020-01-01");
        assertNotNull(temp);
    }

    @Test
    public void temperature_returns_string() {
        UEB7 ueb = new UEB7();
        String query = null;

        Temperature temp = ueb.getTemperature(-3.4123, 1202, "2020-01-01");

        query = temp.createQuery();
        assertNotNull(query);
    }

    @Test
    public void temperature_saves_temperature() {
        UEB7 ueb = new UEB7();
        double temperature = -3.4123;

        Temperature temp = ueb.getTemperature(temperature, 1202, "2020-01-01");
        assertEquals(temperature, temp.getValue());
    }

    @Test
    public void temperature_returns_valid_query() {
        UEB7 ueb = new UEB7();
        double temperature = -3.4123;
        int seconds = 1202;
        String date = "2020-01-01";

        Temperature temp = ueb.getTemperature(temperature, 1202, "2020-01-01");
        String query = temp.createQuery();

        StringBuilder builder = new StringBuilder();
        builder.append("insert into SENSORDATA (TEMPERATURE, SECONDS, DATE) VALUES(");
        builder.append(temperature).append(", ");
        builder.append(seconds).append(", '");
        builder.append(date).append("')");

        boolean isEqual = query.equalsIgnoreCase(builder.toString());
        assertTrue(isEqual);
    }

    @Test
    public void temperature_throws_error_at_wrong_date_format1() {
        UEB7 ueb = new UEB7();
        String query = null;

        assertThrows(() -> {
            Temperature temp = ueb.getTemperature(-3.4123, 1202, "2020-01");
        });
    }

    @Test
    public void temperature_saves_temperature2() {
        UEB7 ueb = new UEB7();
        double temperature = 11.123;

        Temperature temp = ueb.getTemperature(temperature, 1202, "2020-01-01");
        assertEquals(temperature, temp.getValue());
    }

    @Test
    public void temperature_throws_error_at_wrong_date_format2() {
        UEB7 ueb = new UEB7();
        String query = null;

        assertThrows(() -> {
            Temperature temp = ueb.getTemperature(-3.4123, 1202, "2020/01/01");
        });
    }

    @Test
    public void temperature_throws_error_at_wrong_date_format3() {
        UEB7 ueb = new UEB7();
        String query = null;

        assertThrows(() -> {
            Temperature temp = ueb.getTemperature(-3.4123, 1202, "01/01");
        });
    }

    /*
    * ToLower Plugin
     */

    @Test
    public void toLower_checks_headers() {
        UEB7 ueb = new UEB7();
        ueb.getToLowerPlugin();
        ueb.helloWorld();
    }

    @Test
    public void toLower_plugin_returns_error_message_if_no_content() throws Exception{
        UEB7 ueb = new UEB7();
        IPlugin toLower = ueb.getToLowerPlugin();

        IRequest req = new Request(RequestHelper.getValidRequestStream(""));

        assertThrows(() -> {
            IResponse res = toLower.handle(req);
        });
    }

    /*
    * Error Plugin
     */

    @Test
    public void hello_error_plugin() {
        UEB7 ueb = new UEB7();
        IPlugin errorPl = ueb.getErrorPlugin();
        assertNotNull(errorPl);
    }

    @Test
    public void error_plugin_returns_value() throws Exception {
        UEB7 ueb = new UEB7();
        IPlugin errorPl = ueb.getErrorPlugin();

        IRequest req = new Request(RequestHelper.getValidRequestStream("Hello"));
        assertEquals(errorPl.canHandle(req), 0.1f);
    }

    @Test
    public void error_plugin_returns_response() throws Exception {
        UEB7 ueb = new UEB7();
        IPlugin errorPl = ueb.getErrorPlugin();

        IRequest req = new Request(RequestHelper.getValidRequestStream("Hello"));
        IResponse res = errorPl.handle(req);

        assertEquals(res.getStatusCode(), 404);
    }

    @Test
    public void error_plugin_returns_404_status_code() throws Exception {
        UEB7 ueb = new UEB7();
        IPlugin errorPl = ueb.getErrorPlugin();

        IRequest req = new Request(RequestHelper.getValidRequestStream("Hello"));
        IResponse res = errorPl.handle(req);

        assertEquals(res.getStatusCode(), 404);
    }

    @Test
    public void error_plugin_returns_web_page() throws Exception {
        UEB7 ueb = new UEB7();
        IPlugin errorPl = ueb.getErrorPlugin();

        IRequest req = new Request(RequestHelper.getValidRequestStream("Hello"));
        IResponse res = errorPl.handle(req);

        assertTrue(res.getContentLength() > 0);
        assertEquals(res.getContentType(), "text/html");
    }


    /*
    * StaticPlugin
    */



}
