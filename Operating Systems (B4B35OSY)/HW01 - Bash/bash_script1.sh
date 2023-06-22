#!/bin/bash
#file: bash_script1.sh

IFS=$'\n'

HELP=">>>> Arguments usage <<<<
[-h] Help
[-v] At the end of the script numbers of files will be displayed
[-z] At the end of the script will archive all valid files
[-n] Change output format. Put the numbers between FILE/DIR/LINK and adress"

COUNTING=0
CNGFORMAT=0
ERRORAPPEARD=0
TARWANTED=0

while [ $# -gt 0 ]
do
	case "$1" in
		-h) echo "$HELP"; exit 0;;
		-v) COUNTING=1;;
		-z) TARWANTED=1;;
		-n) CNGFORMAT=1;;
		 *) exit 2;;
	esac
	shift
done

FILENUMBER=0
DIRECTORYNUMBER=0
LINKNUMBER=0
ALLFILES=()


while read LINE
do
	if [ ${LINE:0:4} = "FILE" ]
		then
		FILENAME=${LINE:5}
			if [ -L "$FILENAME" ]
				then
				LINKNUMBER=$(( LINKNUMBER+1 ))
				if [ "$CNGFORMAT" = "1" ] 
					then
					echo "LINK $LINKNUMBER '$FILENAME' '$(readlink $FILENAME)'"
				else
					echo "LINK '$FILENAME' '$(readlink $FILENAME)'"
				fi
			elif [ -f "$FILENAME" ]
				then 
				ALLFILES+='"'
				ALLFILES+="$FILENAME"
				ALLFILES+='"'
				ALLFILES+=" "
				FILENUMBER=$(( FILENUMBER+1 ))
				NUMBERLINES=$(wc -l < $FILENAME)
				FIRSTLINE=$(head -n 1 $FILENAME)
				if [ "$CNGFORMAT" = "1" ] 
					then
					echo "FILE $FILENUMBER '$FILENAME' $NUMBERLINES '$FIRSTLINE'"
				else
					echo "FILE '$FILENAME' $NUMBERLINES '$FIRSTLINE'"
				fi
			elif [ -d "$FILENAME" ]
				then
				DIRECTORYNUMBER=$(( DIRECTORYNUMBER+1 ))
				if [ "$CNGFORMAT" = "1" ] 
					then
					echo "DIR $DIRECTORYNUMBER '$FILENAME'"
				else
					echo "DIR '$FILENAME'"
				fi
			else
				ERRORAPPEARD=1				
				(>&2 echo "ERROR '$FILENAME'")
			fi
	fi
done

if [ "$COUNTING" = "1" ]
	then
	echo "$FILENUMBER"
	echo "$DIRECTORYNUMBER"
	echo "$LINKNUMBER"
fi 

if [ "$TARWANTED" = 1 ]
	then
	ALLFILES=${ALLFILES%" "}
	CMD="tar -czf output.tgz "
	CMD+="$ALLFILES"
	eval "$CMD"
fi

if [ "$ERRORAPPEARD" = "1" ]
	then 
	exit 1
fi