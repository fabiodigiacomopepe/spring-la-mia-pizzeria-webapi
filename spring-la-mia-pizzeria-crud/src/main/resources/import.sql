INSERT INTO pizzas (description, name, photo, price) VALUES('pomodoro, mozzarella, basilico', 'Margherita', 'https://assets.tmecosys.com/image/upload/t_web767x639/img/recipe/ras/Assets/5802fab5-fdce-468a-a830-43e8001f5a72/Derivates/c00dc34a-e73d-42f0-a86e-e2fd967d33fe.jpg', 5.20);
INSERT INTO pizzas (description, name, photo, price) VALUES('pomodoro, mozzarella, prosciutto', 'Romana', 'https://media-cdn.tripadvisor.com/media/photo-s/12/8c/f2/ed/pizza-al-prosciutto-cotto.jpg', 5.40);
INSERT INTO pizzas (description, name, photo, price) VALUES('pomodoro, mozzarella, patatine fritte', 'Tedesca', 'https://www.pizzeriahappyburger.it/utenti/happyburger_piazzavittoria/uploads/20220526_195000.jpg',5.60);
INSERT INTO pizzas (description, name, photo, price) VALUES('pomodoro, mozzarella, funghi, acciughe, carciofini sotto olio', 'Capricciosa', 'https://i0.wp.com/pizzabelga.com/wp-content/uploads/2018/12/capricciosa.jpg?fit=500%2C500&ssl=1', 6.50);
INSERT INTO pizzas (description, name, photo, price) VALUES('pomodoro, mozzarella, salame piccante', 'Diavola', 'https://www.lucianopignataro.it/wp-content/uploads/2023/02/Elementi-Pizzeria-Diavola-480x480.png', 5.80);
INSERT INTO pizzas (description, name, photo, price) VALUES('pomodoro, mozzarella, olive nere, capperi, acciughe', 'Napoletana', 'https://img.freepik.com/premium-photo/italian-pizza-with-anchovy-capers-white-background_636803-281.jpg?w=2000', 6.20);
INSERT INTO pizzas (description, name, photo, price) VALUES('pomodoro, mozzarella, speck, funghi', 'Alpina', 'https://www.menu.it/media/ricette/pizza-porcini-speck-e-tartufo-133462/conversions/Spe_k_new-main.jpg', 6.80);
INSERT INTO pizzas (description, name, photo, price) VALUES('pomodoro, mozzarella, tonno, cipolla', 'Tonno e Cipolla', 'https://primochef.it/wp-content/uploads/2022/03/SH_pizza_tonno_e_cipolla.jpg', 6.00);

INSERT INTO ingredients (name) VALUES('pomodoro');
INSERT INTO ingredients (name) VALUES('mozzarella');
INSERT INTO ingredients (name) VALUES('basilico');
INSERT INTO ingredients (name) VALUES('prosciutto');

INSERT INTO roles (id, name) VALUES(1, 'ADMIN');
INSERT INTO roles (id, name) VALUES(2, 'USER');

INSERT INTO users (first_name, last_name, email, password) VALUES('Mario', 'Rossi', 'mariorossi@gmail.com', '{noop}mario');
INSERT INTO users (first_name, last_name, email, password) VALUES('Luca', 'Bianchi', 'lucabianchi@gmail.com', '{noop}luca');

INSERT INTO users_roles (user_id, roles_id) VALUES(1, 1);
INSERT INTO users_roles (user_id, roles_id) VALUES(1, 2);
INSERT INTO users_roles (user_id, roles_id) VALUES(2, 2);