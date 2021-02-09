# TaskPriority

1. Получить список всех объектов **TaskPriority** в JSON формате

   Запрос:<br/>
   ````
   GET /rest/task-priority/list
   ````
   
   Результат:<br/>
   список объектов [TaskPriorityDtoPreview](#description)
   
   Пример строки запроса:<br/>
   ````
   http://localhost:8080/rest/task-priority/list
   ````
   
   <details>
     <summary>Пример ответа:</summary>
   
     ```
     long console output here
     ```
   </details>

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
   
   <a name="description">**TaskPriorityDtoPreview**</a>
   
   Свойство | Описание
   :--- | :---
   ***id*** | идентификатор объекта TaskPriority
   ***name*** | имя приоритета (*Lowest, Low, Medium, High, Highest*)