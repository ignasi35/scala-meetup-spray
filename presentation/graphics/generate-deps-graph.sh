#!/bin/bash


for f in dependencies/*; do 
  for i in `cat $f`; do 
    MODULE=`echo $f | awk -F\/ '{print $(NF)}'`
    echo "[spray-$MODULE]->[spray-$i],"; 
  done ; 
done | xargs echo "http://yuml.me/diagram/scruffy/class/" > out; 
URL=`cat out`; 
wget "$URL" -O image.png ; 
rm out 
