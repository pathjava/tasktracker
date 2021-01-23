//package ru.progwards.tasktracker.repository.deprecated.impl;
//
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//public class ProjectRepositoryUpdateFieldTest {
//
////    @Mock
////    private JsonHandler<Long, ProjectEntity> projectEntityJsonHandler;
////
////    /* в объект repository будет инжектиться объект projectEntityJsonHandler,
////    * а repository будет инжектиться в объект projectEntityRepositoryUpdateField
////    * (данную схему подсмотрел на StackOverFlow)
////    * */
////    @InjectMocks
////    private ProjectRepository repository = Mockito.spy(ProjectRepository.class);
////
////
////    /**
////     * сравниваем значение поля name у ProjectEntity до изменения со значением после изменения
////     */
////    @Test
////    public void updateFieldTest() {
////        Mockito.when(projectEntityJsonHandler.getMap()).thenReturn(new ConcurrentHashMap<>());
////
////        ProjectEntity entity = new ProjectEntity(1L, "name1", "desc1", "", 1L, 1000L, 0L);
////        repository.create(entity);
////
////        String beforeUpdateName = repository.get(entity.getId()).getName();
////        UpdateOneValue updateOneValue = new UpdateOneValue(entity.getId(), "name1777", "name");
////        repository.updateField(updateOneValue);
////        String afterUpdateName = repository.get(entity.getId()).getName();
////
////        Assertions.assertEquals(afterUpdateName, beforeUpdateName + "777");
////    }
//}