load
infile 'productAccessories.csv'
append into table products
fields terminated by","
optionally enclosed by '"'
( 
productid,
productname,
productprice,
catid
)