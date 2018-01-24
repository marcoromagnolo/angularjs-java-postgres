CREATE USER admin WITH PASSWORD 'admin' CREATEDB;

CREATE DATABASE dmt
  WITH OWNER = admin
       TEMPLATE = template0
       ENCODING = 'UTF8'
       CONNECTION LIMIT = -1;
	   
CREATE DATABASE "dmt-test"
  WITH OWNER = admin
       TEMPLATE = template0
       ENCODING = 'UTF8'
       CONNECTION LIMIT = -1;