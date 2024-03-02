## Difference between inner/outer, left/right/full join?
![[Знімок екрана 2024-02-28 о 22.33.45.png |600]]
## When you need to use EXPLAIN? And for what purpose?
EXPLAIN keyword provides a description of how the SQL queries are executed by the databases. These descriptions include the optimizer logs, how tables are joined and in what order, etc. EXPLAIN also indicates the fact that a user who doesn’t have any access to a particular database will not be provided details about how it executes the queries. So it maintains security as well.
## What is CONSTRAIN and how to add it?
SQL constraints are used to specify rules for the data in a table.
Constraints are used to limit the type of data that can go into a table. This ensures the accuracy and reliability of the data in the table. If there is any violation between the constraint and the data action, the action is aborted.
Constraints can be column level or table level. Column level constraints apply to a column, and table level constraints apply to the whole table.
Constraints can be specified when the table is created with the **CREATE TABLE** statement, or after the table is created with the **ALTER TABLE** statement.
## What is ACID?
- Atomicity — неподільності, якщо якась операція проводиться над даними в БД, то в неї немає проміжного стану.
- Consistency — дані залишаються консистентними (незмінність при операціях).
- Isolation — транзакції, які проводяться над даними є ізольованими і не впливають она на одну.
- Durability — якщо дані були збережені в БД, дані повинні існувати навіть після перезавантаження або довготривалої відсутності роботи з БД.
## Difference between WHERE & HAVING?
The difference between the having and where clause in SQL is that the where clause cannot be used with aggregates, but the having clause can. The where clause works on row’s data, not on aggregated data.
## Function vs procedure?
The most significant difference that you should note here is that a function is used to calculate the result using the given inputs, while a procedure is used to perform a certain task in order.

| **Key**                       | **Function**                                                             | **Procedure**                                                                         |
| ----------------------------- | ------------------------------------------------------------------------ | ------------------------------------------------------------------------------------- |
| Definition                    | A function is used to calculate result using given inputs.               | A procedure is used to perform certain task in order.                                 |
| Call                          | A function can be called by a procedure.                                 | A procedure cannot be called by a function.                                           |
| DML                           | DML statements cannot be executed within a function.                     | DML statements can be executed within a procedure.                                    |
| SQL, Query                    | A function can be called within a query.                                 | A procedure cannot be called within a query.                                          |
| SQL, Call                     | Whenever a function is called, it is first compiled before being called. | A procedure is compiled once and can be called multiple times without being compiled. |
| SQL, Return                   | A function returns a value and control to calling function or code.      | A procedure returns the control but not any value to calling function or code.        |
| try-catch                     | A function has no support for try-catch                                  | A procedure has support for try-catch blocks.                                         |
| SELECT                        | A select statement can have a function call.                             | A select statement can't have a procedure call.                                       |
| Explicit Transaction Handling | A function cannot have explicit transaction handling.                    | A procedure can use explicit transaction handling.                                    |
## Indexes in SQL? Type of indexes?
**SQL Indexes** are special lookup tables that are used to speed up the process of data retrieval. They hold pointers that refer to the data stored in a database, which makes it easier to locate the required data records in a database table.
**Types of Indexes**
- Unique Index
- Single-Column Index
- Composite Index
- Implicit Index
## What is sql view?
In SQL, a view is a virtual table based on the result-set of an SQL statement.
A view contains rows and columns, just like a real table. The fields in a view are fields from one or more real tables in the database.
You can add SQL statements and functions to a view and present the data as if the data were coming from one single table.
A view always shows up-to-date data. The database engine recreates the view, every time a user queries it.