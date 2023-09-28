# spring boot wiremock 


- http://localhost:9090/employees
```
{
"data": [
{
"id": 1,
"firstName": "adarsh",
"lastName": "kumar",
"career": "It",
"salary": 10,
"doj": "2020-01-01",
"gender": "MALE",
"attributes": {
"key1": "value"
}
},
{
"id": 2,
"firstName": "radha",
"lastName": "singh",
"career": "IT",
"salary": 10,
"doj": "2020-01-01",
"gender": "FEMALE",
"attributes": {
"key2": "value"
}
},
{
"id": 3,
"firstName": "sonu",
"lastName": "singh",
"career": "IT",
"salary": 5,
"doj": "2020-01-01",
"gender": "MALE",
"attributes": {
"key3": "value"
}
},
{
"id": 4,
"firstName": "amit",
"lastName": "kumar",
"career": "Finance",
"salary": 8,
"doj": "2020-01-01",
"gender": "MALE",
"attributes": {
"key4": "value"
}
}
],
"message": "Successful "
}
```

- http://localhost:9090/employee/1
```
{
"data": {
"id": 1,
"firstName": "adarsh",
"lastName": "kumar",
"career": "It",
"salary": 10,
"doj": "2020-01-01",
"gender": "MALE",
"attributes": {
"key1": "value"
}
},
"message": "Successful"
}
```