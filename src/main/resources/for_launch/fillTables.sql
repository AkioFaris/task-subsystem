INSERT INTO apprz_db.disciplines(id_discipline, name_discipline) VALUES(1, 'discrete mathematics');

INSERT INTO apprz_db.topics(name_topic, id_discipline) VALUES('combinatorics', 1);

INSERT INTO apprz_db.problems(statement, difficulty, id_topic, id_discipline, start_expression, final_expression) 
VALUES('Prove that A(m,n) equals A(m-1,n) + n*A(m-1,n-1)', 1, 1, 1, 'A(m,n)', 'A(m-1,n) + n*A(m-1,n-1)');

INSERT INTO apprz_db.problems(statement, difficulty, id_topic, id_discipline, start_expression, final_expression) 
VALUES('Prove that F(n+3) - F(n) equals 2 * F(n+1)', 2, 1, 1, 'F(n+3) - F(n)', '2 * F(n+1)');

INSERT INTO apprz_db.problems(statement, difficulty, id_topic, id_discipline, start_expression, final_expression) 
VALUES('Prove that B(m) equals S(n,0,m,S1(m,n)/P(n))', 3, 1, 1, 'B(m)', 'S(n,0,m,S1(m,n)/P(n))');

INSERT INTO apprz_db.problems(statement, difficulty, id_topic, id_discipline, start_expression, final_expression) 
VALUES('Prove that B(m) equals S(n,0,m,S1(m,n)/P(n))', 3, 1, 1, 'B(m)', 'S(n,0,m,S1(m,n)/P(n))');

INSERT INTO apprz_db.tests(num_problems, id_discipline, sum_scores) VALUES(3, 1, 100);

INSERT INTO apprz_db.test_problems(id_test, id_problem, score) VALUES(1, 1, 20);

INSERT INTO apprz_db.test_problems(id_test, id_problem, score) VALUES(1, 2, 35);

INSERT INTO apprz_db.test_problems(id_test, id_problem, score) VALUES(1, 3, 45);



