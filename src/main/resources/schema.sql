CREATE DATABASE GeographicInfo;
use GeographicInfo;

create login GeographicInfo with password = 'GeographicInfo', default_database = GeographicInfo, check_expiration = off, check_policy = off;

create user GeographicInfo from LOGIN GeographicInfo;

grant create schema to GeographicInfo;
grant create table to GeographicInfo;
grant create view to GeographicInfo;
grant backup database to GeographicInfo;
grant backup log to GeographicInfo;
grant create default to GeographicInfo;
grant create function to GeographicInfo;
grant create procedure to GeographicInfo;
grant create rule to GeographicInfo;
grant alter on database::GeographicInfo to GeographicInfo;

CREATE TABLE ip_statistics (
                               id BIGINT IDENTITY(1,1) PRIMARY KEY,
                               country_code CHAR(2) NOT NULL,
                               distance DECIMAL(8, 2) NOT NULL,
                               INDEX idx_country_code (country_code)
);