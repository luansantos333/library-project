INSERT INTO tb_book (title, author, price, quantity) VALUES ('A Song of Ice and Fire Vol I - A Game of Thrones', 'GRR Martin', 30.0, 15);
INSERT INTO tb_book (title, author, price, quantity) VALUES ('A Song of Ice and Fire Vol II - A Clash of Kings', 'GRR Martin', 35.0, 12);
INSERT INTO tb_book (title, author, price, quantity) VALUES ('A Song of Ice and Fire Vol III - A Storm of Swords', 'GRR Martin', 35.0, 12);
INSERT INTO tb_book (title, author, price, quantity) VALUES ('A Song of Ice and Fire Vol IV - A Feast for Crows', 'GRR Martin', 35.0, 10);
INSERT INTO tb_book (title, author, price, quantity) VALUES ('A Song of Ice and Fire Vol V - Dance With Dragons', 'GRR Martin', 35.0, 9);

INSERT INTO tb_book (title, author, price, quantity) VALUES ('The Lord of The Rings Vol I - The Fellowship of The Ring', 'JRR Tolkien', 40.0, 25);
INSERT INTO tb_book (title, author, price, quantity) VALUES ('The Lord of The Rings Vol II - The Two Towers', 'JRR Tolkien', 40.0, 22);
INSERT INTO tb_book (title, author, price, quantity) VALUES ('The Lord of The Rings Vol III - The Return of The King', 'JRR Tolkien', 40.0, 23);

INSERT INTO tb_book (title, author, price, quantity) VALUES ('The Banquet', 'Plato', 15.0, 5);

INSERT INTO tb_book (title, author, price, quantity) VALUES ('Crime and Punishment', 'Fyodor Dostoievsky', 30.0, 8);
INSERT INTO tb_book (title, author, price, quantity) VALUES ('Crime and Punishment', 'Fyodor Dostoievsky', 30.0, 8);

INSERT INTO tb_book (title, author, price, quantity) VALUES ('The Hobbit', 'J.R.R. Tolkien', 25.0, 30);
INSERT INTO tb_book (title, author, price, quantity) VALUES ('Harry Potter and the Sorcerer''s Stone', 'J.K. Rowling', 28.0, 50);
INSERT INTO tb_book (title, author, price, quantity) VALUES ('The Name of the Wind', 'Patrick Rothfuss', 32.0, 18);

INSERT INTO tb_book (title, author, price, quantity) VALUES ('It', 'Stephen King', 33.0, 20);
INSERT INTO tb_book (title, author, price, quantity) VALUES ('Frankenstein', 'Mary Shelley', 22.0, 14);
INSERT INTO tb_book (title, author, price, quantity) VALUES ('Dracula', 'Bram Stoker', 20.0, 17);

INSERT INTO tb_book (title, author, price, quantity) VALUES ('Sapiens: A Brief History of Humankind', 'Yuval Noah Harari', 38.0, 40);
INSERT INTO tb_book (title, author, price, quantity) VALUES ('Guns, Germs, and Steel', 'Jared Diamond', 36.0, 11);

INSERT INTO tb_book (title, author, price, quantity) VALUES ('Meditations', 'Marcus Aurelius', 17.0, 7);
INSERT INTO tb_book (title, author, price, quantity) VALUES ('Thus Spoke Zarathustra', 'Friedrich Nietzsche', 26.0, 6);

INSERT INTO tb_book (title, author, price, quantity) VALUES ('Dune', 'Frank Herbert', 37.0, 28);
INSERT INTO tb_book (title, author, price, quantity) VALUES ('1984', 'George Orwell', 23.0, 35);
INSERT INTO tb_book (title, author, price, quantity) VALUES ('Foundation', 'Isaac Asimov', 34.0, 16);

INSERT INTO tb_book (title, author, price, quantity) VALUES ('Pride and Prejudice', 'Jane Austen', 22.0, 21);
INSERT INTO tb_book (title, author, price, quantity) VALUES ('Love in the Time of Cholera', 'Gabriel Garcia Marquez', 27.0, 13);

INSERT INTO tb_book (title, author, price, quantity) VALUES ('Murder on the Orient Express', 'Agatha Christie', 24.0, 19);
INSERT INTO tb_book (title, author, price, quantity) VALUES ('The Da Vinci Code', 'Dan Brown', 29.0, 26);

INSERT INTO tb_book (title, author, price, quantity) VALUES ('Steve Jobs', 'Walter Isaacson', 42.0, 24);
INSERT INTO tb_book (title, author, price, quantity) VALUES ('The Diary of a Young Girl', 'Anne Frank', 19.0, 29);

INSERT INTO tb_book (title, author, price, quantity) VALUES ('The Waste Land and Other Poems', 'T.S. Eliot', 18.0, 4);
INSERT INTO tb_book (title, author, price, quantity) VALUES ('Leaves of Grass', 'Walt Whitman', 20.0, 3);

INSERT INTO tb_book (title, author, price, quantity) VALUES ('To Kill a Mockingbird', 'Harper Lee', 25.0, 45);
INSERT INTO tb_book (title, author, price, quantity) VALUES ('Moby Dick', 'Herman Melville', 28.0, 10);
INSERT INTO tb_book (title, author, price, quantity) VALUES ('War and Peace', 'Leo Tolstoy', 45.0, 7);

INSERT INTO tb_book (title, author, price, quantity) VALUES ('The Subtle Art of Not Giving a F*ck', 'Mark Manson', 21.0, 55);
INSERT INTO tb_book (title, author, price, quantity) VALUES ('Atomic Habits', 'James Clear', 26.0, 60);

INSERT INTO tb_book (title, author, price, quantity) VALUES ('Where the Crawdads Sing', 'Delia Owens', 30.0, 33);
INSERT INTO tb_book (title, author, price, quantity) VALUES ('The Silent Patient', 'Alex Michaelides', 29.0, 27);

INSERT INTO tb_category (name) VALUES ('Science Fiction');
INSERT INTO tb_category (name) VALUES ('Romance');
INSERT INTO tb_category (name) VALUES ('Mystery');
INSERT INTO tb_category (name) VALUES ('Biography');
INSERT INTO tb_category (name) VALUES ('Poetry');
INSERT INTO tb_category (name) VALUES ('Classic Literature');
INSERT INTO tb_category (name) VALUES ('Self-Help');
INSERT INTO tb_category (name) VALUES ('Contemporary');


INSERT INTO tb_book_category (book_id, category_id) VALUES (1, 1);
INSERT INTO tb_book_category (book_id, category_id) VALUES (2, 1);
INSERT INTO tb_book_category (book_id, category_id) VALUES (3, 1);
INSERT INTO tb_book_category (book_id, category_id) VALUES (4, 1);
INSERT INTO tb_book_category (book_id, category_id) VALUES (5, 1);

INSERT INTO tb_book_category (book_id, category_id) VALUES (6, 1);
INSERT INTO tb_book_category (book_id, category_id) VALUES (7, 1);
INSERT INTO tb_book_category (book_id, category_id) VALUES (8, 1);

INSERT INTO tb_book_category (book_id, category_id) VALUES (9, 5);
INSERT INTO tb_book_category (book_id, category_id) VALUES (9, 11);

INSERT INTO tb_book_category (book_id, category_id) VALUES (10, 5); -- Crime
INSERT INTO tb_book_category (book_id, category_id) VALUES (11, 5); -- Crime and Punishment (2nd) ->

INSERT INTO tb_book_category (book_id, category_id) VALUES (12, 1); -- The Hobbit -> Fantasy

INSERT INTO tb_book_category (book_id, category_id) VALUES (13, 1); -- Harry Potter -> Fantasy

INSERT INTO tb_book_category (book_id, category_id) VALUES (14, 1); -- The Name of the Wind -> Fantasy

INSERT INTO tb_book_category (book_id, category_id) VALUES (15, 2); -- It -> Horror

INSERT INTO tb_book_category (book_id, category_id) VALUES (16, 2);

INSERT INTO tb_book_category (book_id, category_id) VALUES (16, 6);

INSERT INTO tb_book_category (book_id, category_id) VALUES (17, 2);

INSERT INTO tb_book_category (book_id, category_id) VALUES (18, 3);

INSERT INTO tb_book_category (book_id, category_id) VALUES (19, 3);

INSERT INTO tb_book_category (book_id, category_id) VALUES (20, 5);


INSERT INTO tb_book_category (book_id, category_id) VALUES (21, 5);

INSERT INTO tb_book_category (book_id, category_id) VALUES (22, 6);

INSERT INTO tb_book_category (book_id, category_id) VALUES (23, 6);


INSERT INTO tb_book_category (book_id, category_id) VALUES (24, 6);

INSERT INTO tb_book_category (book_id, category_id) VALUES (25, 7);


INSERT INTO tb_book_category (book_id, category_id) VALUES (26, 7);


INSERT INTO tb_book_category (book_id, category_id) VALUES (27, 8);
INSERT INTO tb_book_category (book_id, category_id) VALUES (27, 4);

INSERT INTO tb_book_category (book_id, category_id) VALUES (28, 8);
INSERT INTO tb_book_category (book_id, category_id) VALUES (28, 4);

INSERT INTO tb_book_category (book_id, category_id) VALUES (29, 9);

INSERT INTO tb_book_category (book_id, category_id) VALUES (30, 9);
INSERT INTO tb_book_category (book_id, category_id) VALUES (30, 3);


INSERT INTO tb_book_category (book_id, category_id) VALUES (35, 3);


INSERT INTO tb_book_category (book_id, category_id) VALUES (38, 8);
INSERT INTO tb_book_category (book_id, category_id) VALUES (38, 7);
INSERT INTO tb_book_category (book_id, category_id) VALUES (39, 4);
INSERT INTO tb_book_category (book_id, category_id) VALUES (39, 8);
