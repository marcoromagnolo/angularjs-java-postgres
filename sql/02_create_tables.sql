--Create Sequences
CREATE SEQUENCE session_id_seq;
CREATE SEQUENCE session_history_id_seq;
CREATE SEQUENCE user_id_seq;
CREATE SEQUENCE user_role_id_seq;
CREATE SEQUENCE role_id_seq;
CREATE SEQUENCE user_group_id_seq;
CREATE SEQUENCE group_id_seq;
CREATE SEQUENCE user_account_id_seq;
CREATE SEQUENCE user_account_recovery_id_seq;
CREATE SEQUENCE user_setting_id_seq;


--Create Tables
CREATE TABLE session
(
  id bigint NOT NULL DEFAULT nextval('session_id_seq'::regclass),
  version bigint DEFAULT NULL,
  user_id bigint NOT NULL,
  token character varying(255) NOT NULL,
  expiry boolean NOT NULL DEFAULT false,
  create_date timestamp without time zone NOT NULL DEFAULT now(),
  CONSTRAINT pk_session PRIMARY KEY (id)
);
	  

CREATE TABLE session_history
(
  id bigint NOT NULL DEFAULT nextval('session_history_id_seq'::regclass),
  version bigint DEFAULT NULL,
  user_id bigint NOT NULL,
  token character varying(255) NOT NULL,
  expiry boolean NOT NULL DEFAULT false,
  address character varying(255) NOT NULL,
  headers bytea NOT NULL,
  login_date timestamp without time zone NOT NULL,
  logout_date timestamp without time zone,
  CONSTRAINT pk_session_history PRIMARY KEY (id)
);


CREATE TABLE user
(
  id bigint NOT NULL DEFAULT nextval('user_id_seq'::regclass),
  version bigint DEFAULT NULL,
  setting_id bigint NOT NULL,
  blocked boolean NOT NULL,
  create_date timestamp without time zone NOT NULL DEFAULT now(),
  modify_date timestamp without time zone,
  CONSTRAINT pk_user PRIMARY KEY (id)
);


CREATE TABLE user_group
(
  id bigint NOT NULL DEFAULT nextval('user_group_id_seq'::regclass),
  version bigint DEFAULT NULL,
  user_id bigint NOT NULL,
  group_id bigint NOT NULL,
  CONSTRAINT pk_user_group PRIMARY KEY (id)
);


CREATE TABLE group
(
  id bigint NOT NULL DEFAULT nextval('group_id_seq'::regclass),
  version bigint DEFAULT NULL,
  name character varying(255) NOT NULL,
  description character varying(1000),
  CONSTRAINT pk_group PRIMARY KEY (id)
); 


CREATE TABLE user_role
(
  id bigint NOT NULL DEFAULT nextval('user_role_id_seq'::regclass),
  version bigint DEFAULT NULL,
  user_id bigint NOT NULL,
  role_id bigint NOT NULL,
  CONSTRAINT pk_user_role PRIMARY KEY (id)
);


CREATE TABLE role
(
  id bigint NOT NULL DEFAULT nextval('role_id_seq'::regclass),
  version bigint DEFAULT NULL,
  name character varying(255) NOT NULL,
  description character varying(1000),
  CONSTRAINT pk_role PRIMARY KEY (id)
); 

 
CREATE TABLE user_account
(
  id bigint NOT NULL DEFAULT nextval('user_account_id_seq'::regclass),
  version bigint DEFAULT NULL,
  recovery_id bigint DEFAULT NULL,
  user_id bigint NOT NULL,
  first_name character varying(255) NOT NULL,
  last_name character varying(255) NOT NULL,
  email character varying(255) NOT NULL,
  username character varying(255) NOT NULL,
  password character varying(255) NOT NULL,
  confirm_code character varying(255) NOT NULL,
  confirm_date timestamp without time zone,
  birthday timestamp without time zone,
  phone character varying(255),
  create_date timestamp without time zone NOT NULL DEFAULT now(),
  modify_date timestamp without time zone,
  CONSTRAINT pk_user_account PRIMARY KEY (id)
);

 
CREATE TABLE user_account_recovery
(
  id bigint NOT NULL DEFAULT nextval('user_account_recovery_id_seq'::regclass),
  version bigint DEFAULT NULL,
  temp_password character varying(255),
  valid_date timestamp without time zone,
  create_date timestamp without time zone DEFAULT now(),
  CONSTRAINT pk_user_account_recovery PRIMARY KEY (id)
);


CREATE TABLE user_setting
(
  id bigint NOT NULL DEFAULT nextval('user_setting_id_seq'::regclass),
  version bigint DEFAULT NULL,
  locale character varying(255),
  timezone character varying(255),
  create_date timestamp without time zone DEFAULT now(),
  modify_date timestamp without time zone,
  CONSTRAINT pk_user_setting PRIMARY KEY (id)
);
  
  