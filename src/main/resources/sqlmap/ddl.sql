CREATE TABLE `member` (
	`no` BIGINT(20) NOT NULL AUTO_INCREMENT,
	`email` VARCHAR(255) NOT NULL COMMENT '대소문자구분' COLLATE 'utf8mb4_bin',
	`pass` VARCHAR(255) NOT NULL COMMENT '대소문자구분' COLLATE 'utf8mb4_bin',
	`name` VARCHAR(255) NOT NULL COLLATE 'utf8mb4_unicode_ci',
	`nick_name` VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8mb4_unicode_ci',
	`create_at` TIMESTAMP NOT NULL DEFAULT current_timestamp(),
	`update_at` TIMESTAMP NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
	PRIMARY KEY (`no`) USING BTREE,
	UNIQUE INDEX `nick_name` (`nick_name`) USING BTREE
)


CREATE TABLE posts(
	no BIGINT PRIMARY KEY AUTO_INCREMENT,
	title VARCHAR(255) NOT NULL,
	content TEXT NOT NULL,
	read_count INT DEFAULT 0,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP(), 
	updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP() ON UPDATE CURRENT_TIMESTAMP()

);

