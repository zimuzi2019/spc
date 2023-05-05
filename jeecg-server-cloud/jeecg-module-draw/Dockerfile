FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER jeecgos@163.com

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

RUN mkdir -p /jeecg-draw-cloud

WORKDIR /jeecg-draw-cloud

EXPOSE 7004

ADD ./target/jeecg-draw-cloud-start-3.4.4.jar ./

CMD sleep 10;java -Dfile.encoding=utf-8 -Djava.security.egd=file:/dev/./urandom -jar jeecg-draw-cloud-start-3.4.4.jar
