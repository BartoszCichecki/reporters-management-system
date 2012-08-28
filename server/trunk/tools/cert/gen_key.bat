:: Project:   Reporters Management System - Server
:: File:      gen_key.bat
:: License: 
::            This file is licensed under GNU General Public License version 3
::            http://www.gnu.org/licenses/gpl-3.0.txt
::
:: Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
:: Date:      26-08-2012

:: Suggested passwords: abc123

SET CONFIG=openssl.cnf
SET IANAME=server
SET VALIDFOR=3650

openssl genrsa -out ca.key 2048 -des3
openssl req -new -x509 -days %VALIDFOR% -key ca.key -out ca.crt -config %CONFIG%
openssl genrsa -out %IANAME%.key 2048
openssl req -new -key %IANAME%.key -out %IANAME%.csr -config %CONFIG%
openssl x509 -req -days 3650 -in %IANAME%.csr -CA ca.crt -CAkey ca.key -set_serial 01 -out %IANAME%.crt -
openssl pkcs12 -export -out %IANAME%.p12 -inkey %IANAME%.key -in %IANAME%.crt -chain -CAfile ca.crt