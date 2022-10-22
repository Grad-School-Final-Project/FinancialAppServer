-- Insert a default user (ALL) which can be referenced by other tables
-- to apply data to all users. For example categories.
INSERT INTO users(username, first_name, last_name) VALUES
                                                       ('ALL', 'ALL', 'ALL');

-- Insert Default Categories for all users.
INSERT INTO category(category_id, category_name, parent_category_category_id, user_id) VALUES
                                                                                           (0, 'Uncategorized', null, 'ALL');

INSERT INTO category(category_id, category_name, parent_category_category_id, user_id) VALUES
                                                                                           (1, 'Hide from Budgets and Trends', null, 'ALL');