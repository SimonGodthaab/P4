INSERT INTO brands (Id,Name) VALUES (1,'Subaru');
INSERT INTO brands (Id,Name) VALUES (2,'BMW');
INSERT INTO brands (Id,Name) VALUES (3,'Ford');

select * from brands;

INSERT INTO models (Id, Name) VALUES (1, 'Mustang');
INSERT INTO models (Id, Name) VALUES (2, 'Impreza WRX STI');
INSERT INTO models (Id, Name) VALUES (3, 'I8');
INSERT INTO models (Id, Name) VALUES (4, 'M3 E36');

select * from models;

insert into cars (idCars, carBrand_Id, carModel_Id, Price) values (1, 1, 2, 250000.25);
insert into cars (idCars, carBrand_Id, carModel_Id, Price) values (2, 2, 3, 1000000);
insert into cars (idCars, carBrand_Id, carModel_Id, Price) values (4, 2, 4, 600000);
insert into cars (idCars, carBrand_Id, carModel_Id, Price) values (5, 3, 1, 7500000);

Select * from cars;
