/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Hp
 * Created: Jun 6, 2023
 */
CREATE TABLE IF NOT EXISTS drone_state (
    stateid INT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(255) NOT NULL,
    status TINYINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
INSERT INTO drone_state(description,status, created_at)VALUES('idle','1',now()),('loading','1',now()),('loaded','1',now()),('delivered','1',now()),('delivering','1',now()),('returned','1',now());

CREATE TABLE IF NOT EXISTS drone_model (
    modelid INT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(255) NOT NULL,
    status TINYINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
INSERT INTO drone_model(description, status, created_at)VALUES('lightweight','1',now()),('middleweight','1',now()),('cruiserweight','1',now()),('heavyweight','1',now());

CREATE TABLE IF NOT EXISTS role
(
     id INT AUTO_INCREMENT PRIMARY KEY,
    role_description VARCHAR(255) NOT NULL,
    role_name VARCHAR(255) NOT NULL
);

INSERT  INTO role(id,role_description,role_name) values (1,'Admin User','admin'),(2,'User','user');



