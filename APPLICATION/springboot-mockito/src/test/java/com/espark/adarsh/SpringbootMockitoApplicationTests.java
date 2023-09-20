package com.espark.adarsh;

import com.espark.adarsh.bean.ResponseBean;
import com.espark.adarsh.entity.Employee;
import com.espark.adarsh.filter.EmployeeFilter;
import com.espark.adarsh.filter.FilterField;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;


@SpringBootTest
@AutoConfigureMockMvc
class SpringbootMockitoApplicationTests {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ResourceLoader resourceLoader;


	@Autowired
	ObjectMapper objectMapper;

	static String employeesResponse = null;
	static String employeeResponse = null;
	static String employeeSaveRequest = null;
	static String employeeUpdateRequest= null;


	@PostConstruct
	public void init() throws IOException {
		employeesResponse = resourceLoader.getResource("classpath:/store/employeesResponse.json")
				.getContentAsString(Charset.defaultCharset());
		employeeResponse = resourceLoader.getResource("classpath:/store/employeeResponse.json")
				.getContentAsString(Charset.defaultCharset());
		employeeSaveRequest = resourceLoader.getResource("classpath:/store/input/employeeSaveRequest.json")
				.getContentAsString(Charset.defaultCharset());
		employeeUpdateRequest = resourceLoader.getResource("classpath:/store/input/employeeUpdateRequest.json")
				.getContentAsString(Charset.defaultCharset());
	}

	@Test
	void contextLoads() {
	}


	@Test
	public void testGetAllEmployees() throws Exception {
		String responseData = this.mockMvc.perform(MockMvcRequestBuilders.get("/employees"))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn().getResponse().getContentAsString();
		  ResponseBean<List<Employee>> responseBean =  objectMapper.readValue(responseData
				  , new TypeReference<ResponseBean<List<Employee>>>() {
		   });
		System.out.println(responseBean.getData());
	}

	@Test
	public void testGetEmployees() throws Exception {
		String responseData = this.mockMvc.perform(MockMvcRequestBuilders.get("/employee/1"))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn().getResponse().getContentAsString();
		ResponseBean<Employee> responseBean =  objectMapper.readValue(responseData
				, new TypeReference<ResponseBean<Employee>>() {
				});
		System.out.println(responseBean.getData());
	}

	@Test
	public void testGetFilterEmployees() throws Exception {
		EmployeeFilter filter = new EmployeeFilter();
		filter.setSalary(new FilterField() {
			{
				setOperator("gt");
				setValue("5");
			}
		});
		String responseData = this.mockMvc.perform(MockMvcRequestBuilders.post("/employee/filter")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(filter)))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn().getResponse().getContentAsString();
		ResponseBean<Iterable<Employee>> responseBean =  objectMapper.readValue(responseData
				, new TypeReference<ResponseBean<Iterable<Employee>>>() {
				});
		System.out.println(responseBean.getData());
	}


	@Test
	public void testSaveEmployees() throws Exception {

		String responseData = this.mockMvc.perform(MockMvcRequestBuilders.post("/employee")
						.contentType(MediaType.APPLICATION_JSON)
						.content(employeeSaveRequest))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn().getResponse().getContentAsString();
		ResponseBean<Employee> responseBean =  objectMapper.readValue(responseData
				, new TypeReference<ResponseBean<Employee>>() {
				});
		System.out.println(responseBean.getData());
	}

	@Test
	public void testUpdateEmployees() throws Exception {

		String responseData = this.mockMvc.perform(MockMvcRequestBuilders.put("/employee")
						.contentType(MediaType.APPLICATION_JSON)
						.content(employeeUpdateRequest))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn().getResponse().getContentAsString();
		ResponseBean<Employee> responseBean =  objectMapper.readValue(responseData
				, new TypeReference<ResponseBean<Employee>>() {
				});
		System.out.println(responseBean.getData());
	}


}
