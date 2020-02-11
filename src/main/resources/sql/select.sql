//Just to help
Insert into ACCOUNTS values (1, "Adriano", "Ribeiro", 100000);
Insert into ACCOUNTS values (2, "Juca", "Ribas", 50000);
Insert into ACCOUNTS values (3, "Tiburcio", "Tebas", 40000);

//Just to help
Insert into TRANSFERS values (1, 1, 2, 800, now());
Insert into TRANSFERS values (2, 1, 2, 300, now());
Insert into TRANSFERS values (3, 2, 3, 1100, now());
Insert into TRANSFERS values (4, 3, 1, 50, now());
Insert into TRANSFERS values (5, 3, 1, 5000, "2018-01-01");

//sql query that finds all accounts that in total transferred more than 1000$ to other people starting from 2019-01-01
SELECT SOURCE_ID, sum(AMOUNT) FROM TRANSFERS 
WHERE TRANSFER_TIME>"2019-01-01"
group by SOURCE_ID having sum(AMOUNT)>1000;
