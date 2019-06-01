create user shoppingproject identified by password;​ 
grant dba, resource, connect to shoppingproject;
conn shoppingproject/password;

select * from shoppinguser;

create table shoppinguser(
  UserID number constraints user_UserID_PK primary key,
  UserType varchar2(20),
  UserName varchar2(20),
  Password varchar2(20),
  BirthDate date,
  Gender varchar2(10),
  Email varchar2(30),
  Contact varchar2(20),
  Address varchar2(50));
  
  create table shoppingproduct(
  ProductID number constraints product_ProductID_PK primary key,
  ProductType varchar2(20),
  ProductName varchar2(20),
  Explanation varchar2(50),
  Price number,
  Inventory number);
  
  create table shoppingbasket(
  BasketID number constraints basket_BasketID_PK primary key,
  UserID number,
  ProductID number,
  Numbers number,
  Validity number);
  
  drop table shoppingbasket;

  create table shoppingpayment(
  PaymentID number constraints payment_PaymentID_PK primary key,
  UserID number,
  ProductID number,
  Numbers number,
  Address varchar2(50),
  Contact varchar2(20),
  CreditCardNumber varchar2(20),
  CreditCardPassword varchar2(10));
  
insert into shoppingproduct values(1, 'Laptop', 'Gigabyte P55K V5',
'Display Screen 15 inch, Chip set made by Intel, i7', 1500, 150);
insert into shoppingproduct values(2, 'Mouse', 'Gigabyte GM-M6900',
'Interface USB, Maximum sensitivity 32', 20, 300);
insert into shoppingproduct values(3, 'Mainboard', 'Gigabyte GA-B85M-D3H',
'Socket Intel-Socket1150, mATX of Standard', 100, 50);
  

insert into shoppingbasket values(1, 1, 1, 3, 1);

SELECT COUNT(BasketID) FROM shoppingbasket