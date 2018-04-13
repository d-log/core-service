package com.loggerproject.coreservice.server;

import com.loggerproject.coreservice.server.data.document.directory.DirectoryModel;
import com.loggerproject.coreservice.server.service.data.directory.create.DirectoryModelCreateService;
import com.loggerproject.coreservice.server.service.data.directory.get.DirectoryModelGetService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Running this will auto create embedded mongo server
 * the reason why we need the spring.data.mongodb.port to be on a diff port so it wont collide with the local running instance
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoreServiceApplication.class)
@TestPropertySource(locations="classpath:test.properties") // overrides application.properties
public class CoreServiceApplicationTests {

	@Autowired
	DirectoryModelCreateService directoryModelCreateService;

	@Autowired
	DirectoryModelGetService directoryModelGetService;

	@Value("${spring.data.mongodb.port}")
	private Integer port;

	@Autowired
	MongoTemplate mongoTemplate;

	@Before
	public void setUp() throws Exception {
		System.out.println("the mongo port number: " + port.toString());
		DirectoryModel directory = new DirectoryModel();
		directory.setName("Test Title");
		directory.setDescription("Test Description");
		directoryModelCreateService.create(directory);
		directoryModelCreateService.create(directory);
		directoryModelCreateService.create(directory);
		directoryModelCreateService.create(directory);
	}

	@Test
	public void simpleGet() {
		List<DirectoryModel> directories = directoryModelGetService.findAll();

		for (DirectoryModel directory : directories) {
			System.out.println(directory.toString());
		}
	}

	@Test
	public void paging() {
		Pageable pageable = new PageRequest(0, 2);
		Query query = new Query().with(pageable);
		List<DirectoryModel> list = mongoTemplate.find(query, DirectoryModel.class);
		Page<DirectoryModel> pagedDirectories = PageableExecutionUtils.getPage(
				list,
				pageable,
				() -> mongoTemplate.count(query, DirectoryModel.class));

		pagedDirectories.forEach(directoryModel -> {
			System.out.println(directoryModel.toString());
		});

	}
}
