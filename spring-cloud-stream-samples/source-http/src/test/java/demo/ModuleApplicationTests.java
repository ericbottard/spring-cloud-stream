package demo;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.cloud.stream.module.source.http.main.ModuleApplication;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ModuleApplication.class)
@WebAppConfiguration
@DirtiesContext
public class ModuleApplicationTests {

	@Test
	public void contextLoads() {
	}

}
