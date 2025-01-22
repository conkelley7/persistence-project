/*
INSERT INTO Campaign(id, code, name, description) VALUES (1, 'C1', 'Campaign 1', 'Description of Campaign 1');
INSERT INTO Campaign(id, code, name, description) VALUES (2, 'C2', 'Campaign 2', 'About Campaign 2');
INSERT INTO Campaign(id, code, name, description) VALUES (3, 'C3', 'Campaign 3', 'About Campaign 3');

INSERT INTO Worker(id, email, first_name, last_name) VALUES(1, 'john@test.com', 'John', 'Doe');

INSERT INTO Task(id, uuid, name, due_date, description, campaign_id, status) VALUES (1, uuid(), 'Task 1', '2025-01-12', 'Task 1 Description', 1, 0);
INSERT INTO Task(id, uuid, name, due_date, description, campaign_id, status) VALUES (2, uuid(), 'Task 2', '2025-02-10', 'Task 2 Description', 1, 0);
INSERT INTO Task(id, uuid, name, due_date, description, campaign_id, status) VALUES (3, uuid(), 'Task 3', '2025-03-16', 'Task 3 Description', 1, 0);
INSERT INTO Task(id, uuid, name, due_date, description, campaign_id, status, assignee_id) VALUES (4, uuid(), 'Task 4', '2025-06-25', 'Task 4 Description', 2, 0, 1);
*/