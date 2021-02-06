# RelationType

## RelationTypeController

### get(Long id) <br />
#### Запрос: <br />
`http://localhost:8080/rest/relationtype/1`

#### Ответ: <br />
Если запрашиваемый RelationType существует в БД:
```json
{
  "id": 1,
  "name": "blocks",
  "counterRelationId": null
}
``` 
Если запрашиваемый RelationType отсутствует в БД: <br />

```json
{
  "timestamp": "2021-02-06 06:46:48",
  "status": "NOT_FOUND",
  "message": "RelationType id=1 not found",
  "errors": [
    "RelationType id=1 not found"
  ]
}
```

### getList() <br />

### create(RelationTypeDtoFull dtoFull) <br />

### update(Long id, RelationTypeDtoFull dtoFull) <br />

### delete(Long id) <br />


```json
{
  "id": null,
  "name": "blocks",
  "counterRelationId": null
}
```

```json
{
  "id": 1,
  "name": "blocks",
  "counterRelationId": null
}
```

```json
{
  "id": null,
  "name": "is blocked by",
  "counterRelationId": 1
}
```

```json
{
  "id": 2,
  "name": "is blocked by",
  "counterRelationId": 1
}
```

```json
{
  "timestamp": "2021-02-06 06:46:48",
  "status": "NOT_FOUND",
  "message": "RelationType id=11 not found",
  "errors": [
    "RelationType id=11 not found"
  ]
}
```