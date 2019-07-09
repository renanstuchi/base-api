FROM aallam/oracle-java
MAINTAINER aallam

ENV TOMCAT_MAJOR_VERSION=8
ENV TOMCAT_VERSION=8.5.41
ENV TOMCAT_HOME=/opt/tomcat
ENV MYSQL_DATABASE=base_api_dev

#workaround for HTRPS issue
RUN printf "deb http://archive.debian.org/debian/ jessie main\ndeb-src http://archive.debian.org/debian/ jessie main\ndeb http://security.debian.org jessie/updates main\ndeb-src http://security.debian.org jessie/updates main" > /etc/apt/sources.list

RUN apt-get update && apt-get install -y apt-transport-https

# adding mysql
RUN apt-get update && \
  apt-get -yq install mysql-server supervisor && \
  rm -rf /var/lib/apt/lists/*

WORKDIR /tmp

# adding tomcat
RUN groupadd tomcat && \
    useradd -s /bin/false -g tomcat -d $TOMCAT_HOME tomcat && \
    mkdir $TOMCAT_HOME && \
    wget http://mirrors.standaloneinstaller.com/apache/tomcat/tomcat-8/v$TOMCAT_VERSION/bin/apache-tomcat-$TOMCAT_VERSION.tar.gz && \
    tar xzvf apache-tomcat-8*tar.gz -C $TOMCAT_HOME --strip-components=1 && \
    chown -R tomcat:tomcat $TOMCAT_HOME && \
    chmod -R g+r $TOMCAT_HOME/conf && \
    chmod g+x $TOMCAT_HOME/conf && \
    rm -rf apache-tomcat-$TOMCAT_VERSION.tar.gz

# adding maven and check if java 8
RUN apt-get update && apt-get install -y maven
RUN java -version

WORKDIR /

# copy your config files to docker. IMPORTANT !
ADD ./scripts/bind_0.cnf /etc/mysql/conf.d/bind_0.cnf
#ADD ./scripts/tomcat-run.sh /tomcat-run.sh
ADD ./scripts/run.sh /run.sh
ADD ./scripts/tomcat-users.xml $TOMCAT_HOME/conf/tomcat-users.xml
ADD ./scripts/supervisord-mysql.conf /etc/supervisor/conf.d/supervisord-mysql.conf
#ADD ./scripts/supervisord-tomcat.conf /etc/supervisor/conf.d/supervisord-tomcat.conf
ADD ./scripts/supervisord-deploy.conf /etc/supervisor/conf.d/supervisord-deploy.conf
ADD ./scripts/settings.xml $TOMCAT_HOME/conf/settings.xml
ADD ./scripts/context.xml $TOMCAT_HOME/webapps/manager/META-INF/context.xml

ADD ./scripts/deploy_api.sh /deploy_api.sh

WORKDIR /app

RUN mkdir /app/api
RUN mkdir /app/api/src

ADD pom.xml /app/api
ADD mvnEclipse /app/api/mvnEclipse

WORKDIR /app/api

RUN chmod 755 mvnEclipse

#RUN mvn dependency:go-offline -B
RUN ./mvnEclipse

#ADD src /app/api/src

# STOP the docker if BUILD fails (junit tests or wrong code)
#RUN mvn clean package

WORKDIR /

RUN chmod 755 /*.sh

#COPY ./target/*.war $TOMCAT_HOME/webapps

VOLUME ["/var/lib/mysql"]

EXPOSE 3306 8080

ENTRYPOINT ["/run.sh"]