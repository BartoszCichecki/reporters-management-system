:: Project:   rms-server
:: File:      db-mysql-dump.bat
:: License: 
::            This file is licensed under GNU General Public License version 3
::            http://www.gnu.org/licenses/gpl-3.0.txt
::
:: Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
:: Date:      09-12-2012

@echo off

:: Set up these variables according to your configuration
SET DB_ADDRESS=127.0.0.1
SET DB_PORT=3306
SET DB_ROOT=root
SET DB_ROOT_PASSWD=root

ECHO ${project.name}
ECHO Database dump
ECHO:
ECHO *** WARNING! ***
ECHO:
ECHO This script will restore database contents.
ECHO:
ECHO NOTICE:
ECHO If you modified connection parameters in rms-server.properties and want this script to work you also need to modify this is script and corresponding SQL file!
ECHO Connection parameters:
ECHO  - Database:  %DB_ADDRESS%:%DB_PORT%
ECHO  - As user:   %DB_ROOT%
ECHO:

SET /P DUMP_FILE=Enter name of file you want to restore: 
goto :dump_restore

:dump_restore
ECHO Database dump restore in progress. Please wait...
mysql rms -u %DB_ROOT% -p"%DB_ROOT_PASSWD%" -h %DB_ADDRESS% -P %DB_PORT% < %DUMP_FILE%
ECHO Success.
ECHO:
goto :exit

:exit
@pause
exit /b 1