:: Project:   rms-server
:: File:      rms-server-startup.bat
:: License: 
::            This file is licensed under GNU General Public License version 3
::            http://www.gnu.org/licenses/gpl-3.0.txt
::
:: Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
:: Date:      17-08-2012

@echo off
cd ${mvn.tomcat}\bin
catalina.bat start