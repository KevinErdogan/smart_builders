#!/bin/bash


p1=`./start-cyclicbarrier` &
echo $p1
sleep 0.5
p2=`./start-gregistry` &
echo  $p2
sleep 0.5
p3=`./start-dcvm controler` &
echo $p3
sleep 0.5
p4=`./start-dcvm counter` &
echo $p4
sleep 0.5
p5=`./start-dcvm tv` &
echo $p5
sleep 0.5
p6=`./start-dcvm fridge` &
sleep 0.5
echo $p6
p7=`./start-dcvm solarpanel` &
echo $p7
p8=`./start-dcvm owen` &
sleep 0.5
echo $p8
sleep 0.5
