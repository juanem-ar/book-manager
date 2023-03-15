INSERT INTO roles(id,creation_date,description,name,deleted,update_date)
VALUES('1',NOW(), 'user roles', 'ROLE_USER',FALSE, NOW()),
    ('2',NOW(), 'admin roles', 'ROLE_ADMIN',FALSE, NOW());

-- USERS (5)
-- ROLE_USER (pw encrypted: 12345678)
INSERT INTO `users` (`id`, `account_non_expired`, `account_non_locked`, `address`, `creation_date`, `credentials_non_expired`, `day_of_birth`, `deleted`, `email`, `first_name`, `last_name`, `password`, `phone_number`, `update_date`, `role`, `area_code`, `document_number`, `document_type`)
VALUES
('1', TRUE, TRUE, 'asdasd', NOW(), TRUE, '1998-01-22', FALSE, 'user1@user.com', 'user', '1', '$2a$10$5IVLmuIsu0T4yYCA.eFIIOPFxX8E4UJTNmIE/0qFiDA7AUL9.fnC.', '3515168300', NOW(), '1', '+54', '36124662','DNI'),
('2', TRUE, TRUE, 'asdasd', NOW(), TRUE, '1997-01-22', FALSE, 'user2@user.com', 'user', '2', '$2a$10$5IVLmuIsu0T4yYCA.eFIIOPFxX8E4UJTNmIE/0qFiDA7AUL9.fnC.', '3515168300', NOW(), '1', '+351', '36124662','DNI'),
('3', TRUE, TRUE, 'asdasd', NOW(), TRUE, '1996-01-22', FALSE, 'user3@user.com', 'user', '3', '$2a$10$5IVLmuIsu0T4yYCA.eFIIOPFxX8E4UJTNmIE/0qFiDA7AUL9.fnC.', '3515168300', NOW(), '1', '+351', '36124662','DNI'),
('4', TRUE, TRUE, 'asdasd', NOW(), TRUE, '1995-01-22', FALSE, 'user4@user.com', 'user', '4', '$2a$10$5IVLmuIsu0T4yYCA.eFIIOPFxX8E4UJTNmIE/0qFiDA7AUL9.fnC.', '3515168300', NOW(), '1', '+351', '36124662','DNI'),
('5', TRUE, TRUE, 'asdasd', NOW(), TRUE, '1994-01-22', FALSE, 'user5@user.com', 'user', '5', '$2a$10$5IVLmuIsu0T4yYCA.eFIIOPFxX8E4UJTNmIE/0qFiDA7AUL9.fnC.', '3515168300', NOW(), '1', '+351', '36124662','DNI'),
('6', TRUE, TRUE, 'asdasd', NOW(), TRUE, '1998-01-22', FALSE, 'admin1@user.com', 'admin', '1', '$2a$10$5IVLmuIsu0T4yYCA.eFIIOPFxX8E4UJTNmIE/0qFiDA7AUL9.fnC.', '3515168300', NOW(), '2', '+351', '36124662','DNI'),
('7', TRUE, TRUE, 'asdasd', NOW(), TRUE, '1997-01-22', FALSE, 'admin2@user.com', 'admin', '2', '$2a$10$5IVLmuIsu0T4yYCA.eFIIOPFxX8E4UJTNmIE/0qFiDA7AUL9.fnC.', '3515168300', NOW(), '2', '+351', '36124662','DNI'),
('8', TRUE, TRUE, 'asdasd', NOW(), TRUE, '1996-01-22', FALSE, 'admin3@user.com', 'admin', '3', '$2a$10$5IVLmuIsu0T4yYCA.eFIIOPFxX8E4UJTNmIE/0qFiDA7AUL9.fnC.', '3515168300', NOW(), '2', '+351', '36124662','DNI'),
('9', TRUE, TRUE, 'asdasd', NOW(), TRUE, '1995-01-22', FALSE, 'admin4@user.com', 'admin', '4', '$2a$10$5IVLmuIsu0T4yYCA.eFIIOPFxX8E4UJTNmIE/0qFiDA7AUL9.fnC.', '3515168300', NOW(), '2', '+351', '36124662','DNI'),
('10', TRUE, TRUE, 'asdasd', NOW(), TRUE, '1994-01-22', FALSE, 'admin5@user.com', 'admin', '5', '$2a$10$5IVLmuIsu0T4yYCA.eFIIOPFxX8E4UJTNmIE/0qFiDA7AUL9.fnC.', '3515168300', NOW(), '2', '+351', '36124662','DNI');

-- BUILDINGS
INSERT INTO `buildings` (`id`, `address`, `deleted`, `name`, `phone_number`, `owner_id`)
VALUES
('1', 'address', FALSE, 'Complejo 1', '123213123', '6'),
('2', 'address', FALSE, 'Complejo 2', '123213123', '6');

-- RENTAL UNITS

INSERT INTO `rental_unit` (`id`, `deleted`, `description`, `maximum_amount_of_guests`, `name`, `number_of_bedrooms`, `number_of_rooms`, `pool`, `status`, `buildings_id`)
VALUES
('1', FALSE, 'string', '9', 'Cabaña 1', '2', '5', 'POOL_PRIVATE', 'STATUS_ENABLE', '1'),
('2', FALSE, 'string', '9', 'Cabaña 2', '2', '5', 'POOL_PRIVATE', 'STATUS_ENABLE', '1'),
('3', FALSE, 'string', '5', 'Cabaña 3', '1', '3', 'POOL_PRIVATE', 'STATUS_ENABLE', '1'),
--('4', FALSE, 'string', '5', 'Cabaña 4', '1', '3', 'POOL_PRIVATE', 'STATUS_ENABLE', '1'),
--('5', FALSE, 'string', '5', 'Cabaña 5', '1', '3', 'POOL_PRIVATE', 'STATUS_ENABLE', '1'),
('6', FALSE, 'string', '9', 'Departamento 1', '2', '5', 'POOL_SHARED', 'STATUS_ENABLE', '2'),
('7', FALSE, 'string', '9', 'Departamento 2', '2', '5', 'POOL_SHARED', 'STATUS_ENABLE', '2'),
--('8', FALSE, 'string', '5', 'Departamento 3', '1', '3', 'POOL_SHARED', 'STATUS_ENABLE', '2'),
--('9', FALSE, 'string', '5', 'Departamento 4', '1', '3', 'POOL_SHARED', 'STATUS_ENABLE', '2'),
('10', FALSE, 'string', '5', 'Departamento 5', '1', '3', 'POOL_SHARED', 'STATUS_ENABLE', '2');

-- 20 Reservations
INSERT INTO
`reservations` (`id`, `amount_of_people`, `check_in`, `check_out`, `cost_per_night`, `creation_date`, `debit`, `deleted`, `partial_payment`, `percent`, `status`, `total_amount`, `update_date`, `unit_id`, `user_id`)
VALUES
('1', '1', '2023-05-10', '2023-05-12', '0', NOW(), '0', FALSE, '0', '10', 'STATUS_ACCEPTED', '0', NOW(), '1', '1'),
('2', '1', '2023-05-12', '2023-05-14', '0', NOW(), '0', FALSE, '0', '10', 'STATUS_ACCEPTED', '0', NOW(), '2', '2'),
('3', '1', '2023-05-14', '2023-05-15', '0', NOW(), '0', FALSE, '0', '10', 'STATUS_ACCEPTED', '0', NOW(), '3', '3'),
('4', '1', '2023-05-16', '2023-05-18', '0', NOW(), '0', FALSE, '0', '10', 'STATUS_ACCEPTED', '0', NOW(), '6', '4'),
('5', '1', '2023-05-19', '2023-05-20', '0', NOW(), '0', FALSE, '0', '10', 'STATUS_ACCEPTED', '0', NOW(), '7', '5'),
('6', '1', '2023-05-21', '2023-05-29', '0', NOW(), '0', FALSE, '0', '10', 'STATUS_ACCEPTED', '0', NOW(), '1', '5'),
('7', '1', '2023-06-15', '2023-06-22', '0', NOW(), '0', FALSE, '0', '10', 'STATUS_ACCEPTED', '0', NOW(), '2', '4'),
('8', '1', '2023-06-23', '2023-06-30', '0', NOW(), '0', FALSE, '0', '10', 'STATUS_ACCEPTED', '0', NOW(), '3', '3'),
('9', '1', '2023-07-03', '2023-07-10', '0', NOW(), '0', FALSE, '0', '10', 'STATUS_ACCEPTED', '0', NOW(), '6', '5'),
('10', '1', '2023-07-12', '2023-07-20', '0', NOW(), '0', FALSE, '0', '10', 'STATUS_ACCEPTED', '0', NOW(), '7', '1'),
('11', '1', '2023-02-20', '2023-02-21', '0', NOW(), '0', FALSE, '0', '10', 'STATUS_ACCEPTED', '0', NOW(), '1', '1'),
('12', '1', '2023-02-28', '2023-03-21', '0', NOW(), '0', FALSE, '0', '10', 'STATUS_ACCEPTED', '0', NOW(), '2', '2'),
('13', '1', '2023-03-22', '2023-04-14', '0', NOW(), '0', FALSE, '0', '10', 'STATUS_ACCEPTED', '0', NOW(), '3', '3'),
('14', '1', '2023-04-15', '2023-04-22', '0', NOW(), '0', FALSE, '0', '10', 'STATUS_ACCEPTED', '0', NOW(), '6', '4'),
('15', '1', '2023-04-23', '2023-04-30', '0', NOW(), '0', FALSE, '0', '10', 'STATUS_ACCEPTED', '0', NOW(), '7', '5'),
('16', '1', '2023-02-28', '2023-03-21', '0', NOW(), '0', FALSE, '0', '10', 'STATUS_ACCEPTED', '0', NOW(), '1', '5'),
('17', '1', '2023-03-22', '2023-04-14', '0', NOW(), '0', FALSE, '0', '10', 'STATUS_ACCEPTED', '0', NOW(), '2', '4'),
('18', '1', '2023-04-15', '2023-04-22', '0', NOW(), '0', FALSE, '0', '10', 'STATUS_ACCEPTED', '0', NOW(), '3', '3'),
('19', '1', '2023-04-23', '2023-04-30', '0', NOW(), '0', FALSE, '0', '10', 'STATUS_ACCEPTED', '0', NOW(), '6', '5'),
('20', '1', '2023-05-03', '2023-05-10', '0', NOW(), '0', FALSE, '0', '10', 'STATUS_ACCEPTED', '0', NOW(), '7', '1');

