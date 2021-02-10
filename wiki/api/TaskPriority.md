# TaskPriority

1. Получить список всех объектов [**TaskPriority**](#model) в JSON формате

   Запрос:<br/>
   ````
   GET /rest/task-priority/list
   ````
   
   Результат:<br/>
   список объектов [TaskPriorityDtoPreview](#dtoPreview)
   
   Пример строки запроса:<br/>
   ````
   http://localhost:8080/rest/task-priority/list
   ````
   
   <details>
     <summary>Пример ответа:</summary>
     
     ```json
     [
         {
            "id": 1,
            "name": "Highest"
         },
         {
            "id": 2,
            "name": "High"
         },
         {
            "id": 3,
            "name": "Low"
         },
         {
            "id": 4,
            "name": "Lowest"
         }
     ]
     ```
   </details>
   
   Метод ***get()***

2. Получить один объект [**TaskPriority**](#model) в JSON формате
   
   Запрос:<br/>
   ````
   GET /rest/task-priority/{id}
   ````
  
   Результат:<br/>
   объект [TaskPriorityDtoFull](#dtoFull)
  
   Пример строки запроса:<br/>
   ````
   http://localhost:8080/rest/task-priority/3
   ````
  
   <details>
     <summary>Пример ответа:</summary>
    
     ```json
     {
        "id": 3,
        "name": "Low",
        "value": 3
     }
     ```
   </details>
  
   Метод ***get(Long id)***
   
3. Создать объект [**TaskPriority**](#model)
      
   Запрос:<br/>
   ````
   POST /rest/task-priority/create
   ````
 
   Результат:<br/>
   объект [TaskPriorityDtoFull](#dtoFull)
 
   Пример строки запроса:<br/>
   ````
   http://localhost:8080/rest/task-priority/create
   ````
   
   <details>
     <summary>Пример тела запроса:</summary>
  
     ```json
     {
        "id": null,
        "name": "High",
        "value": 2
     }
     ```
   </details>
 
   <details>
     <summary>Пример ответа:</summary>
   
     ```json
     {
        "id": 2,
        "name": "High",
        "value": 2
     }
     ```
   </details>
 
   Метод ***create(TaskPriorityDtoFull dto)***
   
4. Обновить объект [**TaskPriority**](#model)
         
   Запрос:<br/>
   ````
   POST /rest/task-priority/{id}/update
   ````
    
   Результат:<br/>
   ничего не возвращается
    
   Пример строки запроса:<br/>
   ````
   http://localhost:8080/rest/task-priority/1/update
   ````
      
   <details>
     <summary>Пример тела запроса:</summary>
 
     ```json
     {
        "name": "Highest",
        "value": 0
     }
     ```
   </details>
    
   Метод ***update(Long id, TaskPriorityDtoFull dto)***
   
5. Удалить объект [**TaskPriority**](#model)

   Запрос:<br/>
   ````
   POST /rest/task-priority/{id}/delete
   ````
       
   Результат:<br/>
   ничего не возвращается
   
   Пример строки запроса:<br/>
   ````
   http://localhost:8080/rest/task-priority/1/delete
   ````
   
   Метод ***delete(Long id)***
   
   <br/>
   
<a name="dtoPreview">**TaskPriorityDtoPreview**</a>

Свойство | Тип данных | Описание
:--- | :--- | :---
***id*** | Long | идентификатор объекта TaskPriority
***name*** | String | имя приоритета (*Lowest, Low, Medium, High, Highest*)

<br/>

<a name="dtoFull">**TaskPriorityDtoFull**</a>
  
Свойство | Тип данных | Описание
:--- | :--- | :--- 
***id*** | Long | идентификатор объекта TaskPriority
***name*** | String | имя приоритета (*Lowest, Low, Medium, High, Highest*)
***value*** | Integer | числовое значение приоритета

<br/>

<a name="model">**TaskPriority**</a>

Свойство | Тип данных | Описание
:--- | :--- | :---
***id*** | Long | идентификатор объекта TaskPriority
***name*** | String | имя приоритета (*Lowest, Low, Medium, High, Highest*)
***value*** | Integer | числовое значение приоритета
***tasks*** | List\<Task\> | список задач с данным приоритетом