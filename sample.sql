DELETE FROM user WHERE id = 1;
INSERT INTO user (id, username, password)
VALUES (1, "testing", "$2a$04$L0n5AivIEB4dj5bDKFTGhOPfE082f4lNGS5PtGEoQ3o/ZFFN57m62");

DELETE FROM category WHERE id = 1;
INSERT INTO category (id, user_id, name)
VALUES (1, 1, "Rent");
DELETE FROM category WHERE id = 2;
INSERT INTO category (id, user_id, name)
VALUES (2, 1, "Groceries");
DELETE FROM category WHERE id = 3;
INSERT INTO category (id, user_id, name)
VALUES (3, 1, "Gas");
DELETE FROM category WHERE id = 4;
INSERT INTO category (id, user_id, name)
VALUES (4, 1, "Clothes");
DELETE FROM category WHERE id = 5;
INSERT INTO category (id, user_id, name)
VALUES (5, 1, "Recreation");
DELETE FROM category WHERE id = 6;
INSERT INTO category (id, user_id, name)
VALUES (6, 1, "Utilities");
DELETE FROM category WHERE id = 7;
INSERT INTO category (id, user_id, name)
VALUES (7, 1, "Phone");
DELETE FROM category WHERE id = 8;
INSERT INTO category (id, user_id, name)
VALUES (8, 1, "Coffee");
DELETE FROM category WHERE id = 9;
INSERT INTO category (id, user_id, name)
VALUES (9, 1, "Student Loans");

SET SQL_SAFE_UPDATES = 0;
DELETE FROM budget_item WHERE user_id = 1;
SET SQL_SAFE_UPDATES = 1;

INSERT INTO budget_item (user_id, category_id, amount, time)
VALUES (1, 1, 1500, now());
INSERT INTO budget_item (user_id, category_id, amount, time)
VALUES (1, 2, 400, now());
INSERT INTO budget_item (user_id, category_id, amount, time)
VALUES (1, 3, 350, now());
INSERT INTO budget_item (user_id, category_id, amount, time)
VALUES (1, 4, 100, now());
INSERT INTO budget_item (user_id, category_id, amount, time)
VALUES (1, 4, 450, now());
INSERT INTO budget_item (user_id, category_id, amount, time)
VALUES (1, 7, 50, now());

SET SQL_SAFE_UPDATES = 0;
DELETE FROM expense WHERE user_id = 1;
SET SQL_SAFE_UPDATES = 1;

-- =====================january==========================

INSERT INTO expense(user_id, category_id, amount, note, date)
VALUES (1, 1, 1500, "paid in full", '2017-01-01 18:19:03');

INSERT INTO expense(user_id, category_id, amount, note, date)
VALUES (1, 2, 10, "2 jugs of milk", '2017-01-02 18:19:03');
INSERT INTO expense(user_id, category_id, amount, note, date)
VALUES (1, 2, 200, "a lot of stuff", '2017-01-04 18:19:03');
INSERT INTO expense(user_id, category_id, amount, note, date)
VALUES (1, 2, 200, "party stuff", '2017-01-05 18:19:03');

INSERT INTO expense(user_id, category_id, amount, note, date)
VALUES (1, 2, 200, "dinner stuff", '2017-01-05 18:19:03');

INSERT INTO expense(user_id, category_id, amount, note, date)
VALUES (1, 7, 55, "went over data slightly", '2017-01-30 18:19:03');

INSERT INTO expense(user_id, category_id, amount, note, date)
VALUES (1, 5, 143, "", '2017-01-30 18:19:03');

INSERT INTO expense(user_id, category_id, amount, note, date)
VALUES (1, 4, 300, "needed a new jacket", '2017-01-30 18:19:03');

INSERT INTO expense(user_id, category_id, amount, note, date)
VALUES (1, 3, 100, "filling up", '2017-01-20 18:19:03');


-- =====================december==========================

INSERT INTO expense(user_id, category_id, amount, note, date)
VALUES (1, 1, 1500, "paid", '2016-12-01 18:19:03');

INSERT INTO expense(user_id, category_id, amount, note, date)
VALUES (1, 2, 42, "things", '2016-12-01 18:19:03');
INSERT INTO expense(user_id, category_id, amount, note, date)
VALUES (1, 2, 33, "tomato", '2016-12-01 18:19:03');
INSERT INTO expense(user_id, category_id, amount, note, date)
VALUES (1, 2, 55, "other stuff", '2016-12-01 18:19:03');


INSERT INTO expense(user_id, category_id, amount, note, date)
VALUES (1, 7, 50, "paid in full", '2016-12-01 18:19:03');

INSERT INTO expense(user_id, category_id, amount, note, date)
VALUES (1, 5, 400, "unexpected", '2016-12-01 18:19:03');

INSERT INTO expense(user_id, category_id, amount, note, date)
VALUES (1, 4, 100, "dskf", '2016-12-01 18:19:03');

INSERT INTO expense(user_id, category_id, amount, note, date)
VALUES (1, 3, 50, "half tank", '2016-12-01 18:19:03');

-- =====================november==========================

INSERT INTO expense(user_id, category_id, amount, note, date)
VALUES (1, 1, 1400, "paid", '2016-11-01 18:19:03');

INSERT INTO expense(user_id, category_id, amount, note, date)
VALUES (1, 2, 20, "things", '2016-11-01 18:19:03');
INSERT INTO expense(user_id, category_id, amount, note, date)
VALUES (1, 2, 42, "tomato", '2016-11-01 18:19:03');
INSERT INTO expense(user_id, category_id, amount, note, date)
VALUES (1, 2, 50, "other stuff", '2016-11-01 18:19:03');


INSERT INTO expense(user_id, category_id, amount, note, date)
VALUES (1, 7, 45, "deal", '2016-11-01 18:19:03');

INSERT INTO expense(user_id, category_id, amount, note, date)
VALUES (1, 5, 50, "", '2016-11-01 18:19:03');

INSERT INTO expense(user_id, category_id, amount, note, date)
VALUES (1, 4, 44, "dskf", '2016-11-01 18:19:03');

INSERT INTO expense(user_id, category_id, amount, note, date)
VALUES (1, 3, 10, "half tank", '2016-11-01 18:19:03');


-- =====================october==========================

INSERT INTO expense(user_id, category_id, amount, note, date)
VALUES (1, 1, 1450, "paid", '2016-10-01 18:19:03');

INSERT INTO expense(user_id, category_id, amount, note, date)
VALUES (1, 2, 10, "things", '2016-10-01 18:19:03');
INSERT INTO expense(user_id, category_id, amount, note, date)
VALUES (1, 2, 32, "tomato", '2016-10-01 18:19:03');
INSERT INTO expense(user_id, category_id, amount, note, date)
VALUES (1, 2, 80, "other stuff", '2016-10-01 18:19:03');


INSERT INTO expense(user_id, category_id, amount, note, date)
VALUES (1, 7, 50, "deal", '2016-10-01 18:19:03');

INSERT INTO expense(user_id, category_id, amount, note, date)
VALUES (1, 5, 193, "", '2016-10-01 18:19:03');

INSERT INTO expense(user_id, category_id, amount, note, date)
VALUES (1, 4, 84, "dskf", '2016-10-01 18:19:03');

INSERT INTO expense(user_id, category_id, amount, note, date)
VALUES (1, 3, 190, "half tank", '2016-10-01 18:19:03');


-- =====================september==========================

INSERT INTO expense(user_id, category_id, amount, note, date)
VALUES (1, 1, 1500, "paid", '2016-09-01 18:19:03');

INSERT INTO expense(user_id, category_id, amount, note, date)
VALUES (1, 2, 200, "things", '2016-09-01 18:19:03');
INSERT INTO expense(user_id, category_id, amount, note, date)
VALUES (1, 2, 132, "tomato", '2016-09-01 18:19:03');
INSERT INTO expense(user_id, category_id, amount, note, date)
VALUES (1, 2, 180, "other stuff", '2016-09-01 18:19:03');


INSERT INTO expense(user_id, category_id, amount, note, date)
VALUES (1, 7, 50, "deal", '2016-09-01 18:19:03');

INSERT INTO expense(user_id, category_id, amount, note, date)
VALUES (1, 5, 120, "", '2016-09-01 18:19:03');

INSERT INTO expense(user_id, category_id, amount, note, date)
VALUES (1, 4, 58, "dskf", '2016-09-01 18:19:03');

INSERT INTO expense(user_id, category_id, amount, note, date)
VALUES (1, 3, 22, "half tank", '2016-09-01 18:19:03');

