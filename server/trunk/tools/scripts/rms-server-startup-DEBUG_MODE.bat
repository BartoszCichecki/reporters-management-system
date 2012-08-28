:: Project:   Reporters Management System - Server
:: File:      rms-server-startup-DEBUG_MODE.bat
:: License: 
::            This file is licensed under GNU General Public License version 3
::            http://www.gnu.org/licenses/gpl-3.0.txt
::
:: Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
:: Date:      19-08-2012

@echo off
cd ${mvn.tomcat}\bin
set JPDA_ADDRESS=8000
set JPDA_TRANSPORT=dt_socket
catalina.bat jpda start