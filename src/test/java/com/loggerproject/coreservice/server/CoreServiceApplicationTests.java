package com.loggerproject.coreservice.server;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Running this will auto create embedded mongo server
 * the reason why we need the spring.filedata.mongodb.port to be on a diff port so it wont collide with the local running instance
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoreServiceApplication.class)
@TestPropertySource(locations="classpath:test.properties") // overrides application.properties
public class CoreServiceApplicationTests {
//
//	@Autowired
//	DirectoryModelCreateService directoryModelCreateService;
//
//	@Autowired
//	DirectoryModelGetService directoryModelGetService;
//
//	@Autowired
//	DirectoryModelUpdateService directoryModelUpdateService;
//
//	@Autowired
//	DirectoryModelRepository directoryModelRepository;
//
//	@Autowired
//	LogModelCreateService logModelCreateService;
//
//	@Autowired
//	LogModelRepository logModelRepository;
//
//	@Autowired
//	LogModelGetService logModelGetService;
//
//	@Autowired
//	ObjectMapper objectMapper;
//
//	@Value("${spring.filedata.mongodb.port}")
//	private Integer port;
//
//	@Autowired
//	MongoTemplate mongoTemplate;

	@Test
	public void dummy() {

	}

//	@Before
//	public void setUp() throws Exception {
//		System.out.println("the mongo port number: " + port.toString());
//		DirectoryModel logdirectory = new DirectoryModel();
//		logdirectory.setName("Test Title");
//		logdirectory.setDescription("Test Description");
//		directoryModelCreateService.create(logdirectory);
//		directoryModelCreateService.create(logdirectory);
//		directoryModelCreateService.create(logdirectory);
//		logdirectory.setName("A Tester");
//		directoryModelCreateService.create(logdirectory);
//		directoryModelCreateService.create(logdirectory);
//		directoryModelCreateService.create(logdirectory);
//		directoryModelCreateService.create(logdirectory);
//
//		Date myDate = new Date(System.currentTimeMillis());
//		logdirectory.getMetadata().setCreated(new Date(myDate.getTime() - (10 * 24 * 60 * 60 * 1000)));
//		directoryModelRepository.save(logdirectory);
//
//		LogModel log = new LogModel();
//		log.setLogOrganization(new LogOrganization());
//		log.getLogOrganization().setDirectoryIDs(Collections.singleton(logdirectory.getID()));
//
//		TextPlainLogData textPlainLogData = new TextPlainLogData();
//		textPlainLogData.setText("Hello World");
//
//		LogData logData = new LogData();
//		logData.setLogDataType("TextPlainLogData");
//		logData.setDataString(objectMapper.writeValueAsString(textPlainLogData));
//
//		log.setLogDatas(Collections.singletonList(logData));
//		logModelCreateService.create(log);
//	}
//
//	@Test
//	public void log() throws Exception {
//		Page<LogModel> page = logModelRepository.findByMetadata_CreatedLessThanEqualOrderByMetadata_CreatedDesc(new Date(System.currentTimeMillis()), new PageRequest(0, 2));
//		List<LogModel> list = page.getContent();
//		System.out.println("\n current time");
//		list.forEach(logModel -> System.out.println(logModel.toString()));
//
//		GetterRequest getterRequest = new GetterRequest();
//		getterRequest.setMillisecondThreshold(System.currentTimeMillis());
//		getterRequest.setPageable(new PageRequest(0, 2, new Sort(new Sort.Order(Sort.Direction.DESC, "metadata.created"))));
//		page = logModelGetService.theGetter(getterRequest);
//		list = page.getContent();
//		System.out.println("\n current time");
//		list.forEach(logModel -> System.out.println(logModel.toString()));
//
//		page = logModelRepository.findByMetadata_CreatedLessThanEqualOrderByMetadata_CreatedDesc(new Date(System.currentTimeMillis() - 1000), new PageRequest(0, 2));
//		list = page.getContent();
//		System.out.println("\n a second ago");
//		list.forEach(logModel -> System.out.println(logModel.toString()));
//
//        getterRequest = new GetterRequest();
//        getterRequest.setMillisecondThreshold(System.currentTimeMillis() - 1000);
//        getterRequest.setPageable(new PageRequest(0, 2, new Sort(new Sort.Order(Sort.Direction.DESC, "metadata.created"))));
//		page = logModelGetService.theGetter(getterRequest);
//		list = page.getContent();
//		System.out.println("\n a second ago");
//		list.forEach(logModel -> System.out.println(logModel.toString()));
//	}
//
//	@Test
//	public void simpleGet() {
//		List<DirectoryModel> directories = directoryModelGetService.findAll();
//		directories.forEach(directoryModel -> System.out.println(directoryModel.toString()));
//	}
//
//	@Test
//	public void paging() {
//		Pageable pageable = new PageRequest(0, 2);
//		Query query = new Query().with(pageable);
//
//		List<DirectoryModel> list = mongoTemplate.find(query, DirectoryModel.class);
//		System.out.println("\nList Stuff");
//		list.forEach(directoryModel -> System.out.println(directoryModel.toString()));
//
//		System.out.println("\nPaged Stuff");
//
//		Page<DirectoryModel> pagedDirectories = PageableExecutionUtils.getPage(
//				list,
//				pageable,
//				() -> mongoTemplate.count(query, DirectoryModel.class));
//
//
//		pagedDirectories.forEach(directoryModel -> System.out.println(directoryModel.toString()));
//	}
//
//	@Test
//	public void paging2() {
//		final Pageable pageableRequest = new PageRequest(0, 2);
//		Query query = new Query();
//		query.with(pageableRequest);
//		List<DirectoryModel> list = mongoTemplate.find(query, DirectoryModel.class);
//		list.forEach(directoryModel -> System.out.println(directoryModel.toString()));
//	}
//
//	/**
//	 * regex starts with
//	 */
//	@Test
//	public void regex() {
//		Pageable pageable = new PageRequest(0, 2);
//		Query query = new Query(Criteria.where("name").regex("^A")).with(pageable);
//		List<DirectoryModel> list = mongoTemplate.find(query, DirectoryModel.class);
//		Page<DirectoryModel> pagedDirectories = PageableExecutionUtils.getPage(
//				list,
//				pageable,
//				() -> mongoTemplate.count(query, DirectoryModel.class));
//
//		pagedDirectories.forEach(directoryModel -> System.out.println(directoryModel.toString()));
//	}
//
//	/**
//	 * regex ends with char
//	 */
//	@Test
//	public void regex2() {
//		Query query = new Query();
//		query.addCriteria(Criteria.where("name").regex("r$"));
//		List<DirectoryModel> list = mongoTemplate.find(query, DirectoryModel.class);
//		list.forEach(directoryModel -> System.out.println(directoryModel.toString()));	}
//
//	@Test
//	public void sortASC() {
//		Query query = new Query();
//		query.with(new Sort(Sort.Direction.ASC, "metadata.created"));
//		List<DirectoryModel> list = mongoTemplate.find(query, DirectoryModel.class);
//		list.forEach(directoryModel -> System.out.println(directoryModel.toString()));
//	}
//
//	@Test
//	public void sortDESC() {
//		Query query = new Query();
//		query.with(new Sort(Sort.Direction.DESC, "metadata.created"));
//		List<DirectoryModel> list = mongoTemplate.find(query, DirectoryModel.class);
//		list.forEach(directoryModel -> System.out.println(directoryModel.toString()));
//	}
//
//	@Test
//	public void method1() {
//		Date gt = new Date(System.currentTimeMillis() - (15 * 24 * 60 * 60 * 1000));
//		Date lt = new Date(System.currentTimeMillis() - (5 * 24 * 60 * 60 * 1000));
//
//		List<DirectoryModel> list = directoryModelRepository.findByMetadata_CreatedBetween(gt, lt, new PageRequest(0, 10));
//		list.forEach(directoryModel -> System.out.println(directoryModel.toString()));
//	}
//
//	@Test
//	public void method2WithPaging() {
//		Page<DirectoryModel> page = directoryModelRepository.findByNameLikeOrderByMetadata_CreatedAsc("Test", new PageRequest(0, 2));
//		System.out.println("\n ASC");
//		System.out.println("page number: " + page.getNumber());
//		System.out.println("total elements: " + page.getTotalElements());
//		System.out.println("total pages: " + page.getTotalPages());
//		List<DirectoryModel> list = page.getContent();
//		list.forEach(directoryModel -> System.out.println(directoryModel.toString()));
//
//		page = directoryModelRepository.findByNameLikeOrderByMetadata_CreatedDesc("Test", new PageRequest(0, 2));
//		System.out.println("\n DESC");
//		System.out.println("page number: " + page.getNumber());
//		System.out.println("total elements: " + page.getTotalElements());
//		System.out.println("total pages: " + page.getTotalPages());
//		list = page.getContent();
//		list.forEach(directoryModel -> System.out.println(directoryModel.toString()));
//	}
//
//	@Test
//	public void query1() {
//		List<DirectoryModel> list = directoryModelRepository.findByNameQuery("A Tester");
//		list.forEach(directoryModel -> System.out.println(directoryModel.toString()));
//	}
//
//	@Test
//	public void query2() {
//		// name starts with `A`
//		List<DirectoryModel> list = directoryModelRepository.findByNameRegexQuery("^A");
//		list.forEach(directoryModel -> System.out.println(directoryModel.toString()));
//
//		// name ends with `ter`
//		list = directoryModelRepository.findByNameRegexQuery("ter$");
//		list.forEach(directoryModel -> System.out.println(directoryModel.toString()));
//	}
//
//	@Test
//	public void query2Pageable() {
//		// name starts with `A`
//		List<DirectoryModel> list = directoryModelRepository.findByNameRegexQueryList("^A", new PageRequest(0, 2));
//		list.forEach(directoryModel -> System.out.println(directoryModel.toString()));
//	}
}
