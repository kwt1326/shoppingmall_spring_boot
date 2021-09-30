--PRODUCT INSERT TEST
INSERT INTO PRODUCTS (
    ID,
    CATEGORY,
    DISCOUNT,
    HEART,
    IS_SALEABLE,
    NAME,
    PRICE,
    IMG_SLUG,
    STOCK,
) VALUES (
    0, --first id
    1, --category: Hololive EN == 1
    10, --10% discount
    0, --initialize heart 0
    false, --not sale now
    'Amelia watson',
    300000,
    'ame_product_img',
    10
);

--USER CREATE TEST
INSERT INTO USERS (
    ID,
    EMAIL,
    ENABLED,
    NAME,
    PASSWORD,
    ROLE,
    CONTACT,
    USERNAME,
) VALUES (
    0,
    'u1326@hotmail.com',
    true,
    '홍길동',
    'asdf1234!',
    'ADMIN_ROLE',
    '01012345678',
    'testuser123',
);