:: Project:   Reporters Management System - Server
:: File:      rms-server-shutdown.bat
:: License: 
::            This file is licensed under GNU General Public License version 3
::            http://www.gnu.org/licenses/gpl-3.0.txt
::
:: Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
:: Date:      17-08-2012

cd ${mvn.tomcat}\bin
catalina.bat stop