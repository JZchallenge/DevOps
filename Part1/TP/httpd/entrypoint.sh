#!/bin/sh
sed -i "s/\${BACKEND_HOST}/$BACKEND_HOST/g" /usr/local/apache2/conf/httpd.conf
sed -i "s/\${BACKEND_PORT}/$BACKEND_PORT/g" /usr/local/apache2/conf/httpd.conf
exec httpd-foreground