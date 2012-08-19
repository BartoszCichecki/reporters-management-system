@echo off


SET DB_ADDRESS=${mvn.db.url}
SET DB_PORT=${mvn.db.port}
SET DB_ROOT=root
SET DB_ROOT_PASSWD=root


ECHO ${project.name}
ECHO Database configurator
ECHO:
ECHO *** WARNING! ***
ECHO:
ECHO This script will (re)create users and schemas required by RMS Server in your database!
ECHO NOTICE:
ECHO If you modified connection parameters in rms-server.properties and want this script to work you also need to modify this is script and corresponding SQL file!
ECHO Connection parameters:
ECHO  - Database:  %DB_ADDRESS%:%DB_PORT%
ECHO  - As user:   %DB_ROOT%
ECHO:
SET /P ANS=Are you sure want to continue (Y/N)? 

if /i {%ANS%}=={y} (goto :yes)
if /i {%ANS%}=={yes} (goto :yes)
goto :no


:yes

ECHO Configuring databse... Please wait...
mysql -u %DB_ROOT% -p"%DB_ROOT_PASSWD%" -h %DB_ADDRESS% -P %DB_PORT% < runonce-prepare-db-mysql.sql
ECHO:
@pause
exit /b 0


:no

ECHO Aborting...
ECHO:

@pause
exit /b 1