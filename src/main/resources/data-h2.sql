--USER CREATE TEST
insert into users
    (id, created_at, removed_at, updated_at, email, enabled, name, password, role, contact, username)
values
    (
    null,
    '2021-11-05T09:45:18.987128',
    null,
    '2021-11-05T09:45:18.987157',
    'u1326@hotmail.com',
    1,
    'xJIZON1x',
    '$2a$10$wn0evyClGjwSpb.UIQy5VOmOolqH1NqZ28Dzs9818oUsQziN8UTNW',
    'ADMIN_COMMON',
    01012341234,
    'testusername1'
    );

insert into users
    (id, created_at, removed_at, updated_at, email, enabled, name, password, role, contact, username)
values
    (
    null,
    '2021-11-05T09:45:18.987128',
    null,
    '2021-11-05T09:45:18.987157',
    'u1326@hotmail.com',
    1,
    'xJIZON2x',
    '$2a$10$wn0evyClGjwSpb.UIQy5VOmOolqH1NqZ28Dzs9818oUsQziN8UTNW',
    'USER_COMMON',
    01012341234,
    'user'
    );

--PRODUCT INSERT TEST
--DECLARE @num INT = 0;
--BEGIN
--    FOR num IN 0...21 LOOP
insert into products
    (id, created_at, removed_at, updated_at, category, discount, heart, is_saleable, name, price, img_detail_slug, img_slug, model_slug, publish_at, stock, stop_at, user_id)
values
    (
    null,
    '2021-11-05T10:05:59.047008',
    null,
    '2021-11-05T10:05:59.047067',
    1,
    5.0,
    0,
    true,
    concat('ame_', '0'),
    200000,
    '',
    'ame_img',
    'amelia_watson.glb',
    null,
    20,
    null,
    1
    );
--    END LOOP;
--END;