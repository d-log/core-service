package com.loggerproject.coreservice.server;

import com.loggerproject.coreservice.server.data.document.directory.DirectoryModel;
import com.loggerproject.coreservice.server.data.repository.DirectoryModelRepository;
import com.loggerproject.coreservice.server.service.data.directory.create.DirectoryModelCreateService;
import com.loggerproject.coreservice.server.service.data.directory.get.DirectoryModelGetService;
import com.loggerproject.coreservice.server.service.data.directory.update.DirectoryModelUpdateService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
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

	@Autowired
	DirectoryModelUpdateService directoryModelUpdateService;

	@Autowired
	DirectoryModelRepository directoryModelRepository;

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
		directory.setName("A Tester");
		directoryModelCreateService.create(directory);
		directoryModelCreateService.create(directory);
		directoryModelCreateService.create(directory);
		directoryModelCreateService.create(directory);

		Date myDate = new Date(System.currentTimeMillis());
		directory.getMetadata().setCreated(new Date(myDate.getTime() - (10 * 24 * 60 * 60 * 1000)));
		directoryModelRepository.save(directory);
	}

	@Test
	public void simpleGet() {
		List<DirectoryModel> directories = directoryModelGetService.findAll();
		directories.forEach(directoryModel -> System.out.println(directoryModel.toString()));
	}

	@Test
	public void paging() {
		Pageable pageable = new PageRequest(0, 2);
		Query query = new Query().with(pageable);

		List<DirectoryModel> list = mongoTemplate.find(query, DirectoryModel.class);
		System.out.println("\nList Stuff");
		list.forEach(directoryModel -> System.out.println(directoryModel.toString()));

		System.out.println("\nPaged Stuff");

		Page<DirectoryModel> pagedDirectories = PageableExecutionUtils.getPage(
				list,
				pageable,
				() -> mongoTemplate.count(query, DirectoryModel.class));


		pagedDirectories.forEach(directoryModel -> System.out.println(directoryModel.toString()));
	}

	@Test
	public void paging2() {
		final Pageable pageableRequest = new PageRequest(0, 2);
		Query query = new Query();
		query.with(pageableRequest);
		List<DirectoryModel> list = mongoTemplate.find(query, DirectoryModel.class);
		list.forEach(directoryModel -> System.out.println(directoryModel.toString()));
	}

	/**
	 * regex starts with
	 */
	@Test
	public void regex() {
		Pageable pageable = new PageRequest(0, 2);
		Query query = new Query(Criteria.where("name").regex("^A")).with(pageable);
		List<DirectoryModel> list = mongoTemplate.find(query, DirectoryModel.class);
		Page<DirectoryModel> pagedDirectories = PageableExecutionUtils.getPage(
				list,
				pageable,
				() -> mongoTemplate.count(query, DirectoryModel.class));

		pagedDirectories.forEach(directoryModel -> System.out.println(directoryModel.toString()));
	}

	/**
	 * regex ends with char
	 */
	@Test
	public void regex2() {
		Query query = new Query();
		query.addCriteria(Criteria.where("name").regex("r$"));
		List<DirectoryModel> list = mongoTemplate.find(query, DirectoryModel.class);
		list.forEach(directoryModel -> System.out.println(directoryModel.toString()));	}

	@Test
	public void sortASC() {
		Query query = new Query();
		query.with(new Sort(Sort.Direction.ASC, "metadata.created"));
		List<DirectoryModel> list = mongoTemplate.find(query, DirectoryModel.class);
		list.forEach(directoryModel -> System.out.println(directoryModel.toString()));
	}

	@Test
	public void sortDESC() {
		Query query = new Query();
		query.with(new Sort(Sort.Direction.DESC, "metadata.created"));
		List<DirectoryModel> list = mongoTemplate.find(query, DirectoryModel.class);
		list.forEach(directoryModel -> System.out.println(directoryModel.toString()));
	}

	@Test
	public void method1() {
		Date gt = new Date(System.currentTimeMillis() - (15 * 24 * 60 * 60 * 1000));
		Date lt = new Date(System.currentTimeMillis() - (5 * 24 * 60 * 60 * 1000));

		List<DirectoryModel> list = directoryModelRepository.findByMetadata_CreatedBetween(gt, lt, new PageRequest(0, 10));
		list.forEach(directoryModel -> System.out.println(directoryModel.toString()));
	}

	@Test
	public void method2WithPaging() {
		Page<DirectoryModel> page = directoryModelRepository.findByNameLikeOrderByMetadata_CreatedAsc("Test", new PageRequest(0, 2));
		System.out.println("\n ASC");
		System.out.println("page number: " + page.getNumber());
		System.out.println("total elements: " + page.getTotalElements());
		System.out.println("total pages: " + page.getTotalPages());
		List<DirectoryModel> list = page.getContent();
		list.forEach(directoryModel -> System.out.println(directoryModel.toString()));

		page = directoryModelRepository.findByNameLikeOrderByMetadata_CreatedDesc("Test", new PageRequest(0, 2));
		System.out.println("\n DESC");
		System.out.println("page number: " + page.getNumber());
		System.out.println("total elements: " + page.getTotalElements());
		System.out.println("total pages: " + page.getTotalPages());
		list = page.getContent();
		list.forEach(directoryModel -> System.out.println(directoryModel.toString()));
	}

	@Test
	public void query1() {
		List<DirectoryModel> list = directoryModelRepository.findByNameQuery("A Tester");
		list.forEach(directoryModel -> System.out.println(directoryModel.toString()));
	}

	@Test
	public void query2() {
		// name starts with `A`
		List<DirectoryModel> list = directoryModelRepository.findByNameRegexQuery("^A");
		list.forEach(directoryModel -> System.out.println(directoryModel.toString()));

		// name ends with `ter`
		list = directoryModelRepository.findByNameRegexQuery("ter$");
		list.forEach(directoryModel -> System.out.println(directoryModel.toString()));
	}

	@Test
	public void query2Pageable() {
		// name starts with `A`
		List<DirectoryModel> list = directoryModelRepository.findByNameRegexQueryList("^A", new PageRequest(0, 2));
		list.forEach(directoryModel -> System.out.println(directoryModel.toString()));
	}
}
