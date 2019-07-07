FROM aallam/oracle-java
MAINTAINER aallam

ENV TOMCAT_MAJOR_VERSION=8
ENV TOMCAT_VERSION=8.5.41
ENV TOMCAT_HOME=/opt/tomcat

RUN printf "deb http://archive.debian.org/debian/ jessie main\ndeb-src http://archive.debian.org/debian/ jessie main\ndeb http://security.debian.org jessie/updates main\ndeb-src http://security.debian.org jessie/updates main" > /etc/apt/sources.list

RUN apt-get update && apt-get install -y apt-transport-https

RUN apt-get update && \
  apt-get -yq install mysql-server supervisor && \
  rm -rf /var/lib/apt/lists/*

WORKDIR /tmp

RUN groupadd tomcat && \
    useradd -s /bin/false -g tomcat -d $TOMCAT_HOME tomcat && \
    mkdir $TOMCAT_HOME && \
    wget http://mirrors.standaloneinstaller.com/apache/tomcat/tomcat-8/v$TOMCAT_VERSION/bin/apache-tomcat-$TOMCAT_VERSION.tar.gz && \
    tar xzvf apache-tomcat-8*tar.gz -C $TOMCAT_HOME --strip-components=1 && \
    chown -R tomcat:tomcat $TOMCAT_HOME && \
    chmod -R g+r $TOMCAT_HOME/conf && \
    chmod g+x $TOMCAT_HOME/conf && \
    rm -rf apache-tomcat-$TOMCAT_VERSION.tar.gz

WORKDIR /

ADD bind_0.cnf /etc/mysql/conf.d/bind_0.cnf
ADD mysql-run.sh /mysql-run.sh
ADD tomcat-run.sh /tomcat-run.sh
ADD run.sh /run.sh
ADD tomcat-users.xml $TOMCAT_HOME/conf/tomcat-users.xml
ADD supervisord-mysql.conf /etc/supervisor/conf.d/supervisord-mysql.conf
ADD supervisord-tomcat.conf /etc/supervisor/conf.d/supervisord-tomcat.conf
ADD settings.xml $TOMCAT_HOME/conf/settings.xml
ADD context.xml $TOMCAT_HOME/webapps/manager/META-INF/context.xml

RUN chmod 755 /*.sh

VOLUME ["/var/lib/mysql"]

COPY ./target/renan.war $TOMCAT_HOME/webapps

EXPOSE 3306 8080

ENTRYPOINT ["/run.sh"]