# ecomm
simple bulk ordering
ITEM ORDERNG

Requirements
Java version 1.7. 
Adding spring and other dependencies in pom.xml

Adding items:
- Checks whether the item is already present. If no, then add the item
http://localhost:8087/ecomm/addItem
Content-Type: application/json

Body: 
{
	"item_name":"item5",
	"item_price":"32",
	"quantity_left":"4"
}

Response:
//Response when item is successfully added

{
    "item_id": 7,
    "status": 200
}
//Response when item already exists
{
    "status": 200,
    "msg": "This item already exists"
}

Getting item list:
- getting all the items, all and by ids

http://localhost:8087/ecomm/getItem?item_id=
http://localhost:8087/ecomm/getItem

Response:
[
    {
        "item_id": 1,
        "item_price": 32,
        "item_name": "item1",
        "quantity_left": 0,
        "status": 200
    },
    {
        "item_id": 2,
        "item_price": 32,
        "item_name": "item2",
        "quantity_left": 0,
        "status": 200
    }
]


Bulk Ordering:
- order multiple items all at once
http://localhost:8087/ecomm/makeOrder

{
"email_ids":"abc@abc.com",
"order":"[{\"item_name\":\"item5\",\"quantity\":1},{\"item_name\":\"item6\",\"quantity\":3}]"
} 

Response:
//when the order is successfully placed

{
    "status": 200
}

//when any one item is out of stock

{
    "status": 200,
    "msg": "Items out of stock"
}


Showing orders:
- Can display order by order id or all orders

http://localhost:8087/ecomm/showOrder?order_id=3
http://localhost:8087/ecomm/showOrder

[{
        "email_ids": "abc@abc.com",
        "per_order_list": [
            {
                "quantity": 0,
                "item_id": 1,
                "item_name": "item1"
            },
            {
                "quantity": 0,
                "item_id": 2,
                "item_name": "item2"
            }
        ],
        "order_id": 3,
        "status": 200
    },
    {
     "email_ids": "abc@abc.com",
        "per_order_list": [
            {
                "quantity": 3,
                "item_id": 3,
                "item_name": "item3"
            },
            {
                "quantity": 3,
                "item_id": 4,
                "item_name": "item4"
            }
        ],
        "order_id": 5,
        "status": 200
    }]

