package com.loggerproject.coreservice.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loggerproject.coreservice.server.data.document.directory.DirectoryModel;
import com.loggerproject.coreservice.server.data.document.log.LogModel;
import com.loggerproject.coreservice.server.data.document.log.extra.logdata.LogData;
import com.loggerproject.coreservice.server.data.document.log.extra.logdata.impl.TextPlainLogData;
import com.loggerproject.coreservice.server.data.repository.DirectoryModelRepository;
import com.loggerproject.coreservice.server.data.repository.LogModelRepository;
import com.loggerproject.coreservice.server.service.data.directory.create.DirectoryModelCreateService;
import com.loggerproject.coreservice.server.service.data.directory.get.DirectoryModelGetService;
import com.loggerproject.coreservice.server.service.data.directory.update.DirectoryModelUpdateService;
import com.loggerproject.coreservice.server.service.data.log.create.LogModelCreateService;
import com.loggerproject.coreservice.server.service.data.log.get.regular.LogModelGetService;
import com.loggerproject.coreservice.server.service.data.log.get.regular.getter.model.GetterRequest;
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

import java.util.Collections;
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

	@Autowired
	LogModelCreateService logModelCreateService;

	@Autowired
	LogModelRepository logModelRepository;

	@Autowired
	LogModelGetService logModelGetService;

	@Autowired
	ObjectMapper objectMapper;

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

		LogModel log = new LogModel();
		log.setDirectoryIDs(Collections.singleton(directory.getID()));

		TextPlainLogData textPlainLogData = new TextPlainLogData();
		textPlainLogData.setText("Hello World");

		LogData logData = new LogData();
		logData.setLogDataType("TextPlainLogData");
		logData.setData(objectMapper.writeValueAsString(textPlainLogData));

		log.setLogDatas(Collections.singletonList(logData));
		logModelCreateService.create(log);
	}

	@Test
	public void log() throws Exception {
		Page<LogModel> page = logModelRepository.findByMetadata_CreatedLessThanEqualOrderByMetadata_CreatedDesc(new Date(System.currentTimeMillis()), new PageRequest(0, 2));
		List<LogModel> list = page.getContent();
		System.out.println("\n current time");
		list.forEach(logModel -> System.out.println(logModel.toString()));

		GetterRequest getterRequest = new GetterRequest();
		getterRequest.setMillisecondThreshold(System.currentTimeMillis());
		getterRequest.setPageable(new PageRequest(0, 2, new Sort(new Sort.Order(Sort.Direction.DESC, "metadata.created"))));
		page = logModelGetService.theGetter(getterRequest);
		list = page.getContent();
		System.out.println("\n current time");
		list.forEach(logModel -> System.out.println(logModel.toString()));

		page = logModelRepository.findByMetadata_CreatedLessThanEqualOrderByMetadata_CreatedDesc(new Date(System.currentTimeMillis() - 1000), new PageRequest(0, 2));
		list = page.getContent();
		System.out.println("\n a second ago");
		list.forEach(logModel -> System.out.println(logModel.toString()));

        getterRequest = new GetterRequest();
        getterRequest.setMillisecondThreshold(System.currentTimeMillis() - 1000);
        getterRequest.setPageable(new PageRequest(0, 2, new Sort(new Sort.Order(Sort.Direction.DESC, "metadata.created"))));
		page = logModelGetService.theGetter(getterRequest);
		list = page.getContent();
		System.out.println("\n a second ago");
		list.forEach(logModel -> System.out.println(logModel.toString()));
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
