# TaskPriority

### Методы контроллера:

1. Метод ***get()***

   Данный метод посредством **GET**-запроса позволяет получить список всех объектов ***TaskPriorityDtoPreview***
   
   Пример запроса:<br/>
   http://localhost:8080/rest/task-priority/list

2. Метод ***get(Long id)***

   **входные параметры**:<br/>
   id - идентификатор объекта *TaskPriority*<br/>
   
   Данный метод посредством **GET**-запроса позволяет получить список конкретного объекта 
   ***TaskPriorityDtoFull*** по его идентификатору
   
   Пример запроса:<br/>
   http://localhost:8080/rest/task-priority/1
   
3. Метод ***create(TaskPriorityDtoFull dto)***

   **входные параметры**:<br/>
   dto - создаваемый объект *TaskPriorityDtoFull*
   
   При помощи данного метода создается объект ***TaskPriority*** и сохраняется в СУБД.
   Метод выполняется при помощи **POST**-запроса. Данные передаются в теле запроса при помощи JSON
   формата.
   
   Пример запроса:<br/>
   http://localhost:8080/rest/task-priority/create
   
4. Метод ***update(TaskPriorityDtoFull dto)***

   **входные параметры**:<br/>
   dto - измененный объект *TaskPriorityDtoFull*
   
   При помощи данного метода обновляется объект ***TaskPriority***. Метод выполняется при помощи **POST**-запроса. 
   Данные передаются в теле запроса при помощи JSON формата.
   
   Пример запроса:<br/>
   http://localhost:8080/rest/task-priority/update
   
5. Метод ***delete(Long id)***

   **входные параметры**:<br/>
   id - идентификатор объекта *TaskPriority*<br/>
   
   При помощи данного метода удаляем объект ***TaskPriority*** из СУБД. Метод выполняется при помощи **POST**-запроса.
   
   Пример запроса:<br/>
   http://localhost:8080/rest/task-priority/1/delete