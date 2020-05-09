create table if not exists user (
	pk integer not null auto_increment,
	creation_date datetime not null default current_timestamp,
	modification_date datetime not null default current_timestamp on update current_timestamp,
	handle varchar(200),
	firstname varchar(200),
	lastname varchar(200),
	email varchar(200),
	password varchar(60),
	primary key (pk),
	unique key user_email(email),
	unique key user_handle(handle)
) engine=InnoDB default charset=utf8 collate=utf8_bin;

-- revoked token table does not need to be persistent because token will become invalid each time the instance restarts anyway
create table if not exists revoked_token (
	token varchar(1000) not null,
	expiration_date datetime not null,
	user_fk integer not null,
	primary key (token),
	constraint revoked_token_user foreign key (user_fk) references user(pk)
) engine=Memory default charset=utf8 collate=utf8_bin;

create table if not exists stash (
	pk integer not null auto_increment,
	user_fk integer not null,
	uuid varchar(64) not null,
	creation_date datetime not null default current_timestamp,
	modification_date datetime not null default current_timestamp on update current_timestamp,
	name varchar(200),
	description text,
	primary key (pk),
	unique key stash_uuid(uuid),
	constraint stash_user foreign key (user_fk) references user(pk) on delete cascade
) engine=InnoDB default charset=utf8 collate=utf8_bin;

create table if not exists spot (
	pk integer not null auto_increment,
	user_fk integer not null,
	uuid varchar(64) not null,
	creation_date datetime not null default current_timestamp,
	modification_date datetime not null default current_timestamp on update current_timestamp,
	name varchar(200),
	latitude double default null,
	longitude double default null,
	description text,
	primary key (pk),
	unique key spot_uuid(uuid),
	constraint spot_user foreign key (user_fk) references user(pk) on delete cascade
) engine=InnoDB default charset=utf8 collate=utf8_bin;

create table if not exists stash_spot (
	stash_fk integer not null,
	spot_fk integer not null,
	constraint stash foreign key (stash_fk) references stash(pk) on delete cascade,
	constraint spot foreign key (spot_fk) references spot(pk) on delete cascade
) engine=InnoDB default charset=utf8 collate=utf8_bin;
