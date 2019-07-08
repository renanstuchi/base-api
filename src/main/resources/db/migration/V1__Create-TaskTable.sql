CREATE table task (
	id int(10) unsigned NOT NULL AUTO_INCREMENT,
	name varchar(1024) NOT NULL,
	description varchar(1024) NOT NULL,
	assignedTo varchar(256) NOT NULL,
	status varchar(128) NOT NULL,
  	created timestamp NULL DEFAULT NULL,
  	updated timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;