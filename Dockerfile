FROM nexus.istegelsin.com.tr/base-image-temurin-jdk17-rockylinux:latest
MAINTAINER yildiztech

ADD target/*.jar service.jar