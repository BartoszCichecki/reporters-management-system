:: Project:   rms-server
:: File:      mvn_dependencies_update.bat
:: License: 
::            This file is licensed under GNU General Public License version 3
::            http://www.gnu.org/licenses/gpl-3.0.txt
::
:: Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
:: Date:      27-08-2012

@echo off

cd ..
start mvn versions:display-dependency-updates
start mvn versions:display-plugin-updates