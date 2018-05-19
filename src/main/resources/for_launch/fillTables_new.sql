TRUNCATE TABLE core.disciplines;
TRUNCATE TABLE core.topics;
TRUNCATE TABLE core.problems;
TRUNCATE TABLE core.tests;
TRUNCATE TABLE core.tests_problems;

INSERT INTO core.disciplines(name) VALUES('discrete mathematics');

INSERT INTO core.topics(name, discipline_id) VALUES('combinatorics', 1);

INSERT INTO core.problems(statement, difficulty, discipline_id, topic_id, start_expression, final_expression) 
VALUES('Prove that V(m,n)/A(k,k-m+1) equals A(m+n-1,m+n-1-k)/P(n)', 1, 1, 1, 'V(m,n)/A(k,k-m+1)', 'A(m+n-1,m+n-1-k)/P(n)');

INSERT INTO core.problems(statement, difficulty, discipline_id, topic_id, start_expression, final_expression) 
VALUES('Prove that 2 equals 2', 2, 1, 1, '2', '2');

INSERT INTO core.problems(statement, difficulty, discipline_id, topic_id, start_expression, final_expression) 
VALUES('Prove that 3 equals 3', 3, 1, 1, '3', '3');

INSERT INTO core.problems(statement, difficulty, discipline_id, topic_id, start_expression, final_expression) 
VALUES('Prove that V(m,n)/A(k,k-m+1) + 1 equals A(m+n-1,m+n-1-k)/P(n) + 1', 3, 1, 1, 'V(m,n)/A(k,k-m+1) + 1', 'A(m+n-1,m+n-1-k)/P(n) + 1');

INSERT INTO core.tests(discipline_id) VALUES(1);

INSERT INTO core.tests_problems(problem_id, test_id, score, problem_local_id) VALUES(1, 1, 20, 1);

INSERT INTO core.tests_problems(problem_id, test_id, score, problem_local_id) VALUES(2, 1, 35, 2);

INSERT INTO core.tests_problems(problem_id, test_id, score, problem_local_id) VALUES(3, 1, 45, 3);

INSERT INTO core.tests_problems(problem_id, test_id, score, problem_local_id) VALUES(4, 1, 50, 4);



