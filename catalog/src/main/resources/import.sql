INSERT INTO tb_book (title, author, price) VALUES ('A Song of Ice and Fire Vol I - A Game of Thrones', 'GRR Martin', 30.0);
INSERT INTO tb_book (title, author, price) VALUES ('A Song of Ice and Fire Vol II - A Clash of Kings', 'GRR Martin', 35.0);
INSERT INTO tb_book (title, author, price) VALUES ('A Song of Ice and Fire Vol III - A Storm of Swords', 'GRR Martin', 35.0);
INSERT INTO tb_book (title, author, price) VALUES ('A Song of Ice and Fire Vol IV - A Feast for Crows', 'GRR Martin', 35.0);
INSERT INTO tb_book (title, author, price) VALUES ('A Song of Ice and Fire Vol V - Dance With Dragons', 'GRR Martin', 35.0);

INSERT INTO tb_book (title, author, price) VALUES ('The Lord of The Rings Vol I - The Fellowship of The Ring', 'JRR Tolkien', 40.0);
INSERT INTO tb_book (title, author, price) VALUES ('The Lord of The Rings Vol II - The Two Towers', 'JRR Tolkien', 40.0);
INSERT INTO tb_book (title, author, price) VALUES ('The Lord of The Rings Vol III - The Return of The King', 'JRR Tolkien', 40.0);

INSERT INTO tb_book (title, author, price) VALUES ('The Banquet', 'Plato', 15.0);

INSERT INTO tb_book (title, author, price) VALUES ('Crime and Punishment', 'Fyodor Dostoievsky', 30.0);
INSERT INTO tb_book (title, author, price) VALUES ('Crime and Punishment', 'Fyodor Dostoievsky', 30.0);


INSERT INTO tb_book (title, author, price) VALUES ('The Hobbit', 'J.R.R. Tolkien', 25.0);
INSERT INTO tb_book (title, author, price) VALUES ('Harry Potter and the Sorcerer''s Stone', 'J.K. Rowling', 28.0);
INSERT INTO tb_book (title, author, price) VALUES ('The Name of the Wind', 'Patrick Rothfuss', 32.0);


INSERT INTO tb_book (title, author, price) VALUES ('It', 'Stephen King', 33.0);
INSERT INTO tb_book (title, author, price) VALUES ('Frankenstein', 'Mary Shelley', 22.0);
INSERT INTO tb_book (title, author, price) VALUES ('Dracula', 'Bram Stoker', 20.0);

INSERT INTO tb_book (title, author, price) VALUES ('Sapiens: A Brief History of Humankind', 'Yuval Noah Harari', 38.0);
INSERT INTO tb_book (title, author, price) VALUES ('Guns, Germs, and Steel', 'Jared Diamond', 36.0);


INSERT INTO tb_book (title, author, price) VALUES ('Meditations', 'Marcus Aurelius', 17.0);
INSERT INTO tb_book (title, author, price) VALUES ('Thus Spoke Zarathustra', 'Friedrich Nietzsche', 26.0);

INSERT INTO tb_book (title, author, price) VALUES ('Dune', 'Frank Herbert', 37.0); -- Science Fiction
INSERT INTO tb_book (title, author, price) VALUES ('1984', 'George Orwell', 23.0); -- Science Fiction / Classic Literature
INSERT INTO tb_book (title, author, price) VALUES ('Foundation', 'Isaac Asimov', 34.0); -- Science Fiction

INSERT INTO tb_book (title, author, price) VALUES ('Pride and Prejudice', 'Jane Austen', 22.0); -- Romance / Classic Literature
INSERT INTO tb_book (title, author, price) VALUES ('Love in the Time of Cholera', 'Gabriel Garcia Marquez', 27.0); -- Romance

INSERT INTO tb_book (title, author, price) VALUES ('Murder on the Orient Express', 'Agatha Christie', 24.0); -- Mystery
INSERT INTO tb_book (title, author, price) VALUES ('The Da Vinci Code', 'Dan Brown', 29.0); -- Mystery / Thriller

INSERT INTO tb_book (title, author, price) VALUES ('Steve Jobs', 'Walter Isaacson', 42.0); -- Biography
INSERT INTO tb_book (title, author, price) VALUES ('The Diary of a Young Girl', 'Anne Frank', 19.0); -- Biography / History

INSERT INTO tb_book (title, author, price) VALUES ('The Waste Land and Other Poems', 'T.S. Eliot', 18.0); -- Poetry
INSERT INTO tb_book (title, author, price) VALUES ('Leaves of Grass', 'Walt Whitman', 20.0); -- Poetry

INSERT INTO tb_book (title, author, price) VALUES ('To Kill a Mockingbird', 'Harper Lee', 25.0); -- Classic Literature
INSERT INTO tb_book (title, author, price) VALUES ('Moby Dick', 'Herman Melville', 28.0); -- Classic Literature
INSERT INTO tb_book (title, author, price) VALUES ('War and Peace', 'Leo Tolstoy', 45.0); -- Classic Literature

INSERT INTO tb_book (title, author, price) VALUES ('The Subtle Art of Not Giving a F*ck', 'Mark Manson', 21.0); -- Self-Help
INSERT INTO tb_book (title, author, price) VALUES ('Atomic Habits', 'James Clear', 26.0); -- Self-Help

INSERT INTO tb_book (title, author, price) VALUES ('Where the Crawdads Sing', 'Delia Owens', 30.0); -- Contemporary
INSERT INTO tb_book (title, author, price) VALUES ('The Silent Patient', 'Alex Michaelides', 29.0); -- Contemporary / Thriller

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
 Crime and Pun
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

=
INSERT INTO tb_book_category (book_id, category_id) VALUES (39, 4);
INSERT INTO tb_book_category (book_id, category_id) VALUES (39, 8);
