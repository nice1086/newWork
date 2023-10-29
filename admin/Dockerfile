FROM registry.cn-qingdao.aliyuncs.com/d-common/java:1.8

MAINTAINER dunhf@wdcloud.cc

ENV TZ=Asia/Shanghai JAVA_OPTS="-Xms256m -Xmx1024m -Djava.security.egd=file:/dev/./urandom -D'file.encoding=utf-8' -Dlog4j2.FORMATMsgNoLookups=true"


RUN mkdir -p /vehicle-admin

WORKDIR /vehicle-admin

EXPOSE 9999

ADD ./target/vehicle*.jar ./

ARG PROFILES_ACTIVE=pro

ENV PROFILES_ACTIVE=${PROFILES_ACTIVE}

RUN echo "PROFILES_ACTIVE:${PROFILES_ACTIVE}"

ENTRYPOINT [ "sh", "-c", "exec java $JAVA_OPTS -jar *.jar  --spring.profiles.active=${PROFILES_ACTIVE:pro}"]
