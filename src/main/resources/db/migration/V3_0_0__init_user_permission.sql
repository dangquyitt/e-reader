INSERT INTO roles (name)
VALUES ('USER')
    ON CONFLICT (name) DO NOTHING;

WITH permission_ids AS (
INSERT INTO permissions (http_method, path)
VALUES
    ('GET', '/api/books/filter$')

ON CONFLICT (http_method, path)
    DO NOTHING
    RETURNING id
    ),

-- Lấy id của vai trò 'ADMIN'
user_id AS (
SELECT id
FROM roles
WHERE name = 'USER')

INSERT INTO role_permissions (role_id, permission_id)
SELECT user_id.id, permission_ids.id
FROM user_id, permission_ids
    ON CONFLICT (role_id, permission_id) DO NOTHING;
