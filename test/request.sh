curl -X GET http://localhost:8080/carts

curl -X GET http://localhost:8080/product

curl -X POST http://localhost:8080/carts \
     -H "Content-Type: application/json" \
     -d '{
           "id": 1,
           "items": []
         }'

curl -X GET http://localhost:8080/carts/1

curl -X POST http://localhost:8080/carts/1 \
     -H "Content-Type: application/json" \
     -d '{
           "id": 1,
           "amount": 3,
           "product": {
              "id":"10167771016",
              "name":"【清华】Java从入门到精通(第7七版) java语言程序设计电脑编程基础计算机软件开发教程JAVA编程入门零基础自学书籍javascript Java入门经典","price":35.8,"img":"https://img14.360buyimg.com/n7/jfs/t1/176588/14/42623/190268/65defb2cFde50bf31/cef8c393c8e3a58b.jpg",
              "price": 35.8,
              "img":"https://img14.360buyimg.com/n7/jfs/t1/176588/14/42623/190268/65defb2cFde50bf31/cef8c393c8e3a58b.jpg",
              "category":null,
              "quantity":0,
              "stock":0
          }
    }'

curl -X GET http://localhost:8080/carts/1/checkout

curl -X POST http://localhost:8080/counter/checkout \
     -H "Content-Type: application/json" \
     -d '{
          "id":1,
          "items":[
            {
              "id":1,
              "amount": 3,
              "product": {
                "id":"10167771016",
                "name":"【清华】Java从入门到精通(第7七版) java语言程序设计电脑编程基础计算机软件开发教程JAVA编程入门零基础自学书籍javascript Java入门经典",
                "price":35.8,
                "img":null,
                "category":null,
                "quantity":null,
                "stock":null
              }
            }
          ]
     }'
