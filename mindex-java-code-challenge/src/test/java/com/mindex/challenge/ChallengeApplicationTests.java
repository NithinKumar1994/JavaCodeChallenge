package com.mindex.challenge;

import com.mindex.challenge.controller.CompensationController;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertNotNull;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ChallengeApplicationTests {

	@Test
	public void contextLoads() {
		assertNotNull(CompensationController.class);
		assertNotNull(ReportingStructure.class);
		assertNotNull(Employee.class);
	}

}
