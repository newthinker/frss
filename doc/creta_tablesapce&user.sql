CREATE TABLESPACE "FRSS" 
    LOGGING 
    DATAFILE 'D:\app\zuow\oradata\orcl\FRSS.dbf' SIZE 200M
   AUTOEXTEND
    ON NEXT  100M MAXSIZE UNLIMITED EXTENT MANAGEMENT LOCAL 
    SEGMENT SPACE MANAGEMENT  AUTO ;
  
-- Create the user 
create user FRSS
  identified by "admin"
  default tablespace FRSS
  temporary tablespace TEMP
  profile DEFAULT
  password expire;
-- Modify the password
alter user FRSS  identified by "admin";

-- Grant/Revoke role privileges 
grant connect to FRSS with admin option;
grant dba to FRSS with admin option;
grant exp_full_database to FRSS with admin option;
grant imp_full_database to FRSS with admin option;
grant resource to FRSS with admin option;
-- Grant/Revoke system privileges 
grant unlimited tablespace to FRSS with admin option;
  
