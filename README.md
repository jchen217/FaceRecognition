# FaceRecognition
Face Recognition from Video,Spark to parallelize

This is a program to recognize face from a video
The main idea is get frames from the video to find if there is target person we want to find.
I use spark to compare the face parallelized to improve the performance.
It can be used on data analysis


The program is based on 
Master:
Cpu	Intel(R) Xeon(R) CPU E5-2630 @2.20GHz
core	1
memory	2G
Worker:
Cpu型号	Intel(R) Xeon(R) CPU E5-2630 @2.20GHz
core	1
memory	2G
numbers of worker	3
Hadoop	2.6.0
Spark	2.1.0
Java	1.8.171
JavaCV	1.4.1
Linux 4.4.0-97
