:: Project:   rms-server
:: File:      runonce.bat
:: License: 
::            This file is licensed under GNU General Public License version 3
::            http://www.gnu.org/licenses/gpl-3.0.txt
::
:: Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
:: Date:      17-08-2012

@echo off

:: Set up these variables according to your configuration
SET DB_ADDRESS=127.0.0.1
SET DB_PORT=3306
SET DB_ROOT=root
SET DB_ROOT_PASSWD=root


ECHO ${project.name}
ECHO Database configurator
ECHO:
ECHO *** WARNING! ***
ECHO:
ECHO This script will (re)create users and schemas required by RMS Server in your database.
ECHO:
ECHO NOTICE:
ECHO If you modified connection parameters in rms-server.properties and want this script to work you also need to modify this is script and corresponding SQL file!
ECHO Connection parameters:
ECHO  - Database:  %DB_ADDRESS%:%DB_PORT%
ECHO  - As user:   %DB_ROOT%
ECHO:

SET /P ANS=Are you sure want to continue (Y/N)? 
if /i {%ANS%}=={y} (goto :configure_yes)
if /i {%ANS%}=={yes} (goto :configure_yes)
goto :configure_no

:configure_yes
ECHO Configuring database. Please wait...
mysql -u %DB_ROOT% -p"%DB_ROOT_PASSWD%" -h %DB_ADDRESS% -P %DB_PORT% < runonce-prepare-db-mysql.sql
mysql -u %DB_ROOT% -p"%DB_ROOT_PASSWD%" -h %DB_ADDRESS% -P %DB_PORT% < runonce-prepare-db-mysql-ddl.sql
ECHO Success.
ECHO:
goto :exit

:configure_no
ECHO Aborting...
ECHO:
goto :exit

:exit
@pause
exit /b 1