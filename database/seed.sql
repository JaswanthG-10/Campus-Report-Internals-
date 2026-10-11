-- CampusIssueSystem: Initial Seed Data
USE campus_issue_system;

-- 1. Default admin account
INSERT INTO admins (username, password)
SELECT 'jaswanthgunasekaran.2025.cse@rajalakshmi.edu.in', '123mnb123mnb'
WHERE NOT EXISTS (
    SELECT 1 FROM admins WHERE username = 'jaswanthgunasekaran.2025.cse@rajalakshmi.edu.in'
);

-- 2. Problem categories
INSERT IGNORE INTO problems (problem_id, problem_name, category)
VALUES
(1, 'Fan not working', 'Electrical'),
(2, 'Light not working', 'Electrical'),
(3, 'AC not working', 'Electrical'),
(4, 'Water leakage', 'Plumbing'),
(5, 'Wi-Fi not working', 'Network'),
(6, 'Projector not working', 'Equipment'),
(7, 'Broken desk/chair', 'Furniture'),
(8, 'Cleanliness issue', 'Maintenance'),
(9, 'Washroom issue', 'Maintenance'),
(10, 'Other', 'General')
ON DUPLICATE KEY UPDATE problem_name = VALUES(problem_name);

-- 3. Workpersons
INSERT INTO workpersons (workperson_id, name, specialization, phone)
VALUES
(1, 'Kumar', 'Electrical', '9876543210'),
(2, 'Ravi', 'Plumbing', '9876543211'),
(3, 'Arun', 'Network', '9876543212'),
(4, 'Suresh', 'Maintenance', '9876543213')
ON DUPLICATE KEY UPDATE name = VALUES(name);
