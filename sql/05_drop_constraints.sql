ALTER TABLE user_account DROP CONSTRAINT "user_id";

ALTER TABLE user_account DROP CONSTRAINT "recovery_id";

ALTER TABLE user DROP CONSTRAINT "setting_id";

ALTER TABLE session_history DROP CONSTRAINT "user_id";

ALTER TABLE session DROP CONSTRAINT "user_id";

ALTER TABLE user_group DROP CONSTRAINT "user_id";

ALTER TABLE user_group DROP CONSTRAINT "group_id";

ALTER TABLE user_role DROP CONSTRAINT "user_id";

ALTER TABLE user_role DROP CONSTRAINT "role_id";