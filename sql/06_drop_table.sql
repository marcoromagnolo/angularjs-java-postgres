--Drop Tables
DROP TABLE IF EXISTS session;
DROP TABLE IF EXISTS session_history;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS user_account;
DROP TABLE IF EXISTS user_account_recovery;
DROP TABLE IF EXISTS user_setting;
DROP TABLE IF EXISTS user_group;
DROP TABLE IF EXISTS group;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS user_role;

--Drop Sequences
DROP SEQUENCE IF EXISTS session_id_seq;
DROP SEQUENCE IF EXISTS session_history_id_seq;
DROP SEQUENCE IF EXISTS user_id_seq;
DROP SEQUENCE IF EXISTS user_role_id_seq;
DROP SEQUENCE IF EXISTS role_id_seq;
DROP SEQUENCE IF EXISTS user_group_id_seq;
DROP SEQUENCE IF EXISTS group_id_seq;
DROP SEQUENCE IF EXISTS user_account_id_seq;
DROP SEQUENCE IF EXISTS user_account_recovery_id_seq;
DROP SEQUENCE IF EXISTS user_setting_id_seq;
