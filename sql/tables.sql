CREATE TABLE 'ideapool'.'users' (
'id' int( 11 ) NOT NULL AUTO_INCREMENT ,
'name' varchar( 100 ) NOT NULL ,
'email' varchar( 100 ) NOT NULL ,
'password' varchar( 100 ) NOT NULL ,
'avatar_url' varchar( 100 ) NOT NULL ,
'refresh_token' varchar( 100 ) NOT NULL ,
'created_at'  DATETIME DEFAULT   CURRENT_TIMESTAMP,
'last_modified_at'  DATETIME DEFAULT   CURRENT_TIMESTAMP,
PRIMARY KEY ( 'id' ) ,
UNIQUE KEY 'email' ( 'email' ),
UNIQUE KEY 'refresh_token' ( 'refresh_token' )
) ENGINE = MYISAM DEFAULT CHARSET = utf8;


CREATE TABLE 'ideapool'.'ideas' (
'id' int( 11 ) NOT NULL AUTO_INCREMENT ,
'content' varchar( 255 ) NOT NULL ,
'impact' int(3) not null,
'ease' int(3) not null,
'confidence' int(3) not null,
'created_by' int(11) not null,
'created_at'  DATETIME DEFAULT   CURRENT_TIMESTAMP,
'last_updated_by' int(11) not null,
'last_modified_at'  DATETIME DEFAULT   CURRENT_TIMESTAMP,
PRIMARY KEY ( 'id' )
) ENGINE = MYISAM DEFAULT CHARSET = utf8;