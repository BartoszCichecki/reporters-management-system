## Project:   Reporters Management System - Server
## File:      test_data.sql
## License: 
##            This file is licensed under GNU General Public License version 3
##            http://www.gnu.org/licenses/gpl-3.0.txt
##
## Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
## Date:      26-09-2012

## DELETES
DELETE FROM `rms`.`devices`;
DELETE FROM `rms`.`users`;
DELETE FROM `rms`.`address_data_contacts`;
DELETE FROM `rms`.`address_data`;
DELETE FROM `rms`.`privileges`;
DELETE FROM `rms`.`roles`;

## ROLES
INSERT INTO `rms`.`roles` (`ID`,`CREATED_BY`,`CREATION_DATE`,`MODIFICATION_DATE`,`NAME`,`VERSION`) VALUES (1,null,CURRENT_DATE(),CURRENT_DATE(),'ROLE_1',0);
INSERT INTO `rms`.`roles` (`ID`,`CREATED_BY`,`CREATION_DATE`,`MODIFICATION_DATE`,`NAME`,`VERSION`) VALUES (2,null,CURRENT_DATE(),CURRENT_DATE(),'ROLE_2',0);

## PRIVILEGES
INSERT INTO `rms`.`privileges` (`PRIVILEGE`,`ROLE_ID`) VALUES ('VIEW_DEVICES',1);
INSERT INTO `rms`.`privileges` (`PRIVILEGE`,`ROLE_ID`) VALUES ('MANAGE_DEVICES',1);
INSERT INTO `rms`.`privileges` (`PRIVILEGE`,`ROLE_ID`) VALUES ('VIEW_PROFILE',1);
INSERT INTO `rms`.`privileges` (`PRIVILEGE`,`ROLE_ID`) VALUES ('MANAGE_PROFILE',1);
INSERT INTO `rms`.`privileges` (`PRIVILEGE`,`ROLE_ID`) VALUES ('MANAGE_ROLES',1);
INSERT INTO `rms`.`privileges` (`PRIVILEGE`,`ROLE_ID`) VALUES ('VIEW_USERS',1);
INSERT INTO `rms`.`privileges` (`PRIVILEGE`,`ROLE_ID`) VALUES ('MANAGE_USERS',1);

INSERT INTO `rms`.`privileges` (`PRIVILEGE`,`ROLE_ID`) VALUES ('VIEW_DEVICES',2);
INSERT INTO `rms`.`privileges` (`PRIVILEGE`,`ROLE_ID`) VALUES ('VIEW_PROFILE',2);
INSERT INTO `rms`.`privileges` (`PRIVILEGE`,`ROLE_ID`) VALUES ('VIEW_USERS',2);

## ADDRESS DATA
INSERT INTO `rms`.`address_data` (`CITY`,`COUNTRY`,`CREATION_DATE`,`HOUSE_NUMBER`,`ID`,`MODIFICATION_DATE`,`STREET`,`STREET_NUMBER`,`VERSION`,`ZIP_CODE`) VALUES ('Lodz','Polska',CURRENT_DATE(),1,1,CURRENT_DATE(),'Ulica1',1,0,'11-111');
INSERT INTO `rms`.`address_data` (`CITY`,`COUNTRY`,`CREATION_DATE`,`HOUSE_NUMBER`,`ID`,`MODIFICATION_DATE`,`STREET`,`STREET_NUMBER`,`VERSION`,`ZIP_CODE`) VALUES ('Poznan','Polska',CURRENT_DATE(),1,2,CURRENT_DATE(),'Ulica2',1,0,'21-111');
INSERT INTO `rms`.`address_data` (`CITY`,`COUNTRY`,`CREATION_DATE`,`HOUSE_NUMBER`,`ID`,`MODIFICATION_DATE`,`STREET`,`STREET_NUMBER`,`VERSION`,`ZIP_CODE`) VALUES ('Warszawa','Polska',CURRENT_DATE(),1,3,CURRENT_DATE(),'Ulica3',1,0,'31-111');
INSERT INTO `rms`.`address_data` (`CITY`,`COUNTRY`,`CREATION_DATE`,`HOUSE_NUMBER`,`ID`,`MODIFICATION_DATE`,`STREET`,`STREET_NUMBER`,`VERSION`,`ZIP_CODE`) VALUES ('Krakow','Polska',CURRENT_DATE(),1,4,CURRENT_DATE(),'Ulica4',1,0,'41-111');

## ADDRESS DATA CONTACT
INSERT INTO `rms`.`address_data_contacts` (`ADDRESS_DATA`,`CREATION_DATE`,`ID`,`MODIFICATION_DATE`,`TYPE`,`VALUE`,`VERSION`) VALUES (1,CURRENT_DATE(),1,CURRENT_DATE(),'PHONE','123-456-789',0);
INSERT INTO `rms`.`address_data_contacts` (`ADDRESS_DATA`,`CREATION_DATE`,`ID`,`MODIFICATION_DATE`,`TYPE`,`VALUE`,`VERSION`) VALUES (1,CURRENT_DATE(),2,CURRENT_DATE(),'FAX','123-456-789-000',0);
INSERT INTO `rms`.`address_data_contacts` (`ADDRESS_DATA`,`CREATION_DATE`,`ID`,`MODIFICATION_DATE`,`TYPE`,`VALUE`,`VERSION`) VALUES (2,CURRENT_DATE(),3,CURRENT_DATE(),'FACEBOOK','fbcontact',0);
INSERT INTO `rms`.`address_data_contacts` (`ADDRESS_DATA`,`CREATION_DATE`,`ID`,`MODIFICATION_DATE`,`TYPE`,`VALUE`,`VERSION`) VALUES (2,CURRENT_DATE(),4,CURRENT_DATE(),'SKYPE','skypecontact',0);
INSERT INTO `rms`.`address_data_contacts` (`ADDRESS_DATA`,`CREATION_DATE`,`ID`,`MODIFICATION_DATE`,`TYPE`,`VALUE`,`VERSION`) VALUES (3,CURRENT_DATE(),5,CURRENT_DATE(),'FACEBOOK','fbcontactbis',0);
INSERT INTO `rms`.`address_data_contacts` (`ADDRESS_DATA`,`CREATION_DATE`,`ID`,`MODIFICATION_DATE`,`TYPE`,`VALUE`,`VERSION`) VALUES (4,CURRENT_DATE(),6,CURRENT_DATE(),'SKYPE','skypecontactbis',0);

## USERS
INSERT INTO `rms`.`users` (`ADDRESS`,`COMMENT`,`CREATION_DATE`,`DELETED`,`ID`,`LOCKED`,`MODIFICATION_DATE`,`PASSWORD`,`ROLE`,`USERNAME`,`VERSION`) VALUES (1,'comment_1',current_date(),false,1,false,current_date(),'123',1,'user1',0);
INSERT INTO `rms`.`users` (`ADDRESS`,`COMMENT`,`CREATION_DATE`,`DELETED`,`ID`,`LOCKED`,`MODIFICATION_DATE`,`PASSWORD`,`ROLE`,`USERNAME`,`VERSION`) VALUES (2,'comment_2',current_date(),false,2,false,current_date(),'123',2,'user2',0);

## DEVICES
INSERT INTO `rms`.`devices` (`CREATION_DATE`,`DESCRIPTION`,`ID`,`MODIFICATION_DATE`,`NAME`,`VERSION`) VALUES (current_date(),'desc1',1,current_date(),'dev1',0);
INSERT INTO `rms`.`devices` (`CREATION_DATE`,`DESCRIPTION`,`ID`,`MODIFICATION_DATE`,`NAME`,`VERSION`) VALUES (current_date(),'desc2',2,current_date(),'dev2',0);
INSERT INTO `rms`.`devices` (`CREATION_DATE`,`DESCRIPTION`,`ID`,`MODIFICATION_DATE`,`NAME`,`VERSION`) VALUES (current_date(),'desc3',3,current_date(),'dev3',0);

