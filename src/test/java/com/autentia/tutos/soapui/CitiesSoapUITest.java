package com.autentia.tutos.soapui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.autentia.tutos.springboot.TestApplication;
import com.autentia.tutos.springboot.schema.cities.City;
import com.autentia.tutos.springboot.services.CityService;
import com.eviware.soapui.tools.SoapUITestCaseRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class CitiesSoapUITest {

	@Resource
	private CityService cityService;
	
	private static final String TEST_FILE = "src/test/resources/cities-soapui-project.xml";

	@LocalServerPort
	private int port;
	
	@Test
	public void shouldExecuteAllTestCases() throws Exception {
		cityService.deleteCityByCode(1);
		SoapUITestCaseRunner runner = new SoapUITestCaseRunner();

		runner.setProjectFile(TEST_FILE);
		runner.setEndpoint("http://localhost:".concat(port + "").concat("/ws/cities"));
		runner.setPrintReport(true);
		runner.setJUnitReport(true);
		runner.setExportAll(true);
		runner.setOutputFolder("./target/surefire-reports");
		runner.setIgnoreError(true);

		runner.run();
	}


	@Test
	public void shouldExecuteICTestCase() throws Exception{
		long insertCode = 1;

		SoapUITestCaseRunner runner = new SoapUITestCaseRunner();

		cityService.deleteCityByCode(1);
		runner.setProjectFile(TEST_FILE);
		runner.setEndpoint("http://localhost:".concat(port + "").concat("/ws/cities"));
		runner.setPrintReport(true);
		runner.setJUnitReport(true);
		runner.setExportAll(true);
		runner.setOutputFolder("./target/surefire-reports");
		runner.setIgnoreError(true);
		runner.setTestSuite("Tuto_CitiesWS");
		runner.setTestCase("TestCase IC");
		runner.run();
		City city = cityService.getCityByCode(insertCode);
		assertNotNull(city);
		assertEquals(insertCode,city.getCode());
		assertEquals("TomorrowLand",city.getName());
		assertEquals("Chiquitistan",city.getCountry());
		assertEquals(1,city.getFounded());
		assertEquals(1,city.getPopulation());
	}


}
