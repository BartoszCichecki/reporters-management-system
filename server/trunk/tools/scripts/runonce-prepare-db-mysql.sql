## Project:   Reporters Management System - Server
## File:      runonce-prepare-db-mysql.sql
## License: 
##            This file is licensed under GNU General Public License version 3
##            http://www.gnu.org/licenses/gpl-3.0.txt
##
## Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
## Date:      17-08-2012

DELIMITER ;;

## DROP SCHEMA
DROP SCHEMA IF EXISTS rms;;

## CREATE SCHEMA
CREATE SCHEMA rms;;

## USE CREATED SCHEMA
USE rms;;

## DROP USERS WITH SIMILAR NAMES
DROP PROCEDURE IF EXISTS PREPARE_USERS;;
CREATE PROCEDURE PREPARE_USERS()
BEGIN
    IF EXISTS(SELECT * FROM mysql.user WHERE user='rms') THEN
        SET @users = NULL;        
        SELECT GROUP_CONCAT('\'',user, '\'@\'', host, '\'') INTO @users FROM mysql.user WHERE user = 'rms';
        SET @users = CONCAT('DROP USER ', @users);        
        PREPARE stmt1 FROM @users;
        EXECUTE stmt1;
        DEALLOCATE PREPARE stmt1;    
        FLUSH PRIVILEGES;
    END IF;
END;;
CALL PREPARE_USERS();;
DROP PROCEDURE IF EXISTS PREPARE_USERS;;

## CREATE USER
CREATE USER 'rms'@'127.0.0.1' IDENTIFIED BY 'rms123';;
GRANT ALL ON rms.* TO 'rms'@'127.0.0.1' WITH MAX_USER_CONNECTIONS  250;;

DELIMITER ;
