//package ru.progwards.tasktracker.repository.deprecated.impl;
//
//public class TaskPriorityEntityRepositoryTest {
//
////    @InjectMocks
////    private TaskPriorityRepository repository;
////
////    @Mock
////    private JsonHandler<Long, TaskPriorityEntity> projectEntityJsonHandler;
////
////    private Map<Long, TaskPriorityEntity> map;
////
////    @BeforeEach
////    public void initMock() {
////        MockitoAnnotations.initMocks(this);
////    }
////
////    @Test
////    public void getEntitiesTest() {
////        map = new ConcurrentHashMap<>();
////
////        for (long i = 0; i < 10; i++) {
////            map.put(i, new TaskPriorityEntity(i, "name"+i, (int)i));
////        }
////
////        Mockito.when(projectEntityJsonHandler.getMap()).thenReturn(map);
////
////        Assertions.assertEquals(map.values().size(), repository.get().size());
////    }
////
////    @Test
////    public void getEntityTest() {
////        map = new ConcurrentHashMap<>();
////
////        for (long i = 0; i < 10; i++) {
////            map.put(i, new TaskPriorityEntity(i, "name"+i, (int)i));
////        }
////
////        Mockito.when(projectEntityJsonHandler.getMap()).thenReturn(map);
////
////        TaskPriorityEntity entity = map.get(1L);
////
////        Assertions.assertEquals(entity, repository.get(entity.getId()));
////    }
////
////    @Test
////    public void createTest() {
////        for (long i = 0; i < 10; i++) {
////            repository.create(new TaskPriorityEntity(i, "name"+i, (int)i));
////        }
////
////        Mockito.verify(projectEntityJsonHandler, Mockito.times(10)).write();
////    }
////
////    @Test
////    public void updateTest() {
////        map = new ConcurrentHashMap<>();
////
////        for (long i = 0; i < 10; i++) {
////            map.put(i, new TaskPriorityEntity(i, "name"+i, (int)i));
////        }
////
////        Mockito.when(projectEntityJsonHandler.getMap()).thenReturn(map);
////
////        TaskPriorityEntity entity = projectEntityJsonHandler.getMap().get(1L);
////        String newName = entity.getName() + 23;
////
////        entity.setName(newName);
////
////        repository.update(entity);
////
////        TaskPriorityEntity entityAfterUpdate = projectEntityJsonHandler.getMap().get(1L);
////        String nameAfterUpdate = entityAfterUpdate.getName();
////
////        Assertions.assertEquals(nameAfterUpdate, newName);
////    }
////
////    @Test
////    public void deleteTest() {
////        map = new ConcurrentHashMap<>();
////
////        for (long i = 0; i < 10; i++) {
////            map.put(i, new TaskPriorityEntity(i, "name"+i, (int)i));
////        }
////
////        Mockito.when(projectEntityJsonHandler.getMap()).thenReturn(map);
////
////        TaskPriorityEntity entity = repository.get(3L);
////
////        Assertions.assertNotNull(entity);
////
////        repository.delete(entity.getId());
////
////        TaskPriorityEntity entityAfterDelete = repository.get(3L);
////
////        Assertions.assertNull(entityAfterDelete);
////    }
//}
