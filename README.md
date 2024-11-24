# Synchronized-Databases
Distributed Systems : Synchronized Databases
For this project we assume one Head Office (HO) and 2 Branch offices (BO) for sales. The 2 sales branches are physically separated from the Head office.
They manage their databases independently and they need to synchronise their data to the Head office that maintain the hole data of sales. We assume that
the database are based on the product sales table with the following structure.

![image](https://github.com/user-attachments/assets/5b8b5351-bbf7-4fd8-8161-bd1437e9b552)


The main of this lab is to create a distributed application that synchronisation databases from the product sales tables. This application needs to use 
the RabbitMQ to send data on the related queues. We run 2 distributed processes that synchronise data from the first BO to HO, the second BO to the HO .
For this project we use Java with JDBC connector for MySQL databases for sales. 
3 databases are needed for the 2 BO and one HO. The synchronisation is made by the 2 BO.
