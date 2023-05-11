Select * From useraccounts; 

INSERT INTO useraccounts (firstName, lastName, username, password) VALUES ('Simon', 'Godthaab', 'Root', 'admin');

UPDATE useraccounts SET password = 'Admin'
WHERE username = 'root';

Select count(1) From uuseraccountsseraccounts Where BINARY username = 'root' AND BINARY password = 'Admin'; 

delete from useraccounts where username='root';

rollback;

select * from brands;


select c.idCars, cb.Name, cm.Name, c.Price
from cars c, brands cb, models cm
where c.carBrand_Id = cb.Id 
and c.carModel_Id = cm.Id;

Select cb, carModel_Id, Price;
