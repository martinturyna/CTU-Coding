#!/bin/bash
#file: bash_script1.sh
#Author: Martin Turyna 18.10.2017


HELP=">>>> Arguments usage <<<<
[-h] Help
[-i] Standard input will be expected
[-u] URL adress as input file will be expected"

if [ "$1" = "-h" ]
	then
	echo "$HELP"
	exit 0;
elif [ "$1" = "-u" ]
	then
	HTML=$(wget -q -O - $2)
elif [ "$1" = "-i" ]
	then
	HTML=$(cat)
else
	exit 1;
fi

TEXT="$HTML"

TEXT=$(echo "$TEXT" | tr '\n' '"')


FINAL=$(echo "$TEXT" | grep -o -i -E '< *a[[:space:]]*[^>]+>')
FINAL=$(echo "$FINAL" | grep -o -i -E '[[:space:]]+href *= *"[^"]*"')
FINAL=$(echo "$FINAL" | grep -o -i -E '"[^"]*\.pdf"')   #"[a-zA-Z0-9 .:/_-]+[[:alnum:]]+\.pdf"+')
echo "$FINAL" | grep -o -i -E '[^"]+'

#echo "$FINAL" #| grep -o -i -E '<a +href *= *".*" *>' #| grep -o -i -E '"+[a-zA-Z0-9 .:/]+[[:alnum:]]+\.pdf"+' | grep -o -i -E '[^"]+'