package przewalutowanie.conversion;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class CurrencyConversionApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void conversionTest(){
		RestTemplate restTemplate = new RestTemplate();
		double value = Double.parseDouble(restTemplate.getForObject("http://localhost:8080/convert?currencyFromCode=EUR&currencyToCode=EUR&currencyFromValue=10", String.class));
		assertTrue(value<10);
	}

	@Test
	void currencyTest(){
		RestTemplate restTemplate = new RestTemplate();
		assertEquals("200 OK", restTemplate.getForEntity("http://localhost:8080/convert?currencyFromCode=BBB&currencyToCode=EUR&currencyFromValue=10", String.class).getStatusCode().toString());
		assertEquals("This currency cannot be converted or value is less then 0", restTemplate.getForObject("http://localhost:8080/convert?currencyFromCode=BBB&currencyToCode=EUR&currencyFromValue=10", String.class));
	}

}
