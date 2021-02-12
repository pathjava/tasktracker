# TaskPriority

Объект [***TaskPriority***](#model) - это приоритет выполнения задачи. При помощи ТaskPriority пользователь видит, 
какой приоритет у поставленных ему задач и на основании этого принимает решение по очередности их выполнения. 
Вот список приоритетов от самого низкого к самому высокому: Lowest, Low, Medium, High, Highest.

### 1. Получить список всех объектов [**TaskPriority**](#model) в JSON формате

   Запрос:<br/>
   ````
   GET /rest/taskpriority/list
   ````
   
   Результат:<br/>
   список объектов [TaskPriorityDtoPreview](#dtoPreview)
   
   Пример строки запроса:<br/>
   ````
   http://localhost:8080/rest/taskpriority/list
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

### 2. Получить один объект [**TaskPriority**](#model) в JSON формате
   
   Запрос:<br/>
   ````
   GET /rest/taskpriority/{id}
   ````
  
   Результат:<br/>
   объект [TaskPriorityDtoFull](#dtoFull)
  
   Пример строки запроса:<br/>
   ````
   http://localhost:8080/rest/taskpriority/3
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
   
### 3. Создать объект [**TaskPriority**](#model)
      
   Запрос:<br/>
   ````
   POST /rest/taskpriority/create
   ````
 
   Результат:<br/>
   объект [TaskPriorityDtoFull](#dtoFull)
 
   Пример строки запроса:<br/>
   ````
   http://localhost:8080/rest/taskpriority/create
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
   
### 4. Обновить объект [**TaskPriority**](#model)
         
   Запрос:<br/>
   ````
   POST /rest/taskpriority/{id}/update
   ````
    
   Результат:<br/>
   ничего не возвращается
    
   Пример строки запроса:<br/>
   ````
   http://localhost:8080/rest/taskpriority/1/update
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
   
### 5. Удалить объект [**TaskPriority**](#model)

   Запрос:<br/>
   ````
   POST /rest/taskpriority/{id}/delete
   ````
       
   Результат:<br/>
   ничего не возвращается
   
   Пример строки запроса:<br/>
   ````
   http://localhost:8080/rest/taskpriority/1/delete
   ````
   
   Метод ***delete(Long id)***
   
   <br/>
   
### <a name="dtoPreview">**TaskPriorityDtoPreview**</a>

*Используется для ссылочного отображения, для использования в списках*

Свойство | Тип данных | Описание
:--- | :--- | :---
***id*** | Long | идентификатор объекта TaskPriority
***name*** | String | имя приоритета (*Lowest, Low, Medium, High, Highest*)

<br/>

### <a name="dtoFull">**TaskPriorityDtoFull**</a>

*Используется для создания объекта TaskPriority, для модификации и для просмотра*
  
Свойство | Тип данных | Описание
:--- | :--- | :--- 
***id*** | Long | идентификатор объекта TaskPriority
***name*** | String | имя приоритета (*Lowest, Low, Medium, High, Highest*)
***value*** | Integer | числовое значение приоритета

<br/>

### <a name="model">**TaskPriority**</a>
 
*Используется для хранения в СУБД, для реализации внутренней бизнес-логики*

Свойство | Тип данных | Описание
:--- | :--- | :---
***id*** | Long | идентификатор объекта TaskPriority
***name*** | String | имя приоритета (*Lowest, Low, Medium, High, Highest*)
***value*** | Integer | числовое значение приоритета
***tasks*** | List\<Task\> | список задач с данным приоритетом