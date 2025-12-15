INSERT INTO TB_Company (company_id, company_name, connected_date, status)
VALUES (1, 'Test Company', NOW(), 'CONNECTED')
    ON DUPLICATE KEY UPDATE company_name = 'Test Company';

INSERT INTO TB_User (user_id, login_id, login_pwd, name, role, company_id)
VALUES (1, 'test_login', 'test_pwd', 'Test User', 'USER', 1)
    ON DUPLICATE KEY UPDATE name = 'Test User';