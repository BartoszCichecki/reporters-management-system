:: Project:   rms-server
:: File:      maintenance-edit-properties.bat
:: License: 
::            This file is licensed under GNU General Public License version 3
::            http://www.gnu.org/licenses/gpl-3.0.txt
::
:: Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
:: Date:      19-08-2012

cd ..\${mvn.tomcat}\webapps\ROOT
start notepad rms-server.properties