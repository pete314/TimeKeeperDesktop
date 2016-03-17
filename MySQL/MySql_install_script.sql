--please create a database called tk_remote

CREATE TABLE if not exists `users` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(300) NOT NULL,
  `password` varchar(150) NOT NULL,
  `email` varchar(350) NOT NULL,
  `full_name` varchar(300) NOT NULL,
  `created` datetime DEFAULT NULL,
  `updated` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1 |

CREATE TABLE if not exists `items` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `local_id` int(11) unsigned NOT NULL,
  `category` tinyint(1) unsigned NOT NULL,
  `title` varchar(350) NOT NULL,
  `description` text,
  `finish_date` date DEFAULT NULL,
  `created` datetime NOT NULL,
  `updated` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `user_id` int(11) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=latin1 |