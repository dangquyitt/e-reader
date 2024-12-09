INSERT INTO roles (name)
VALUES ('ADMIN')
ON CONFLICT (name) DO NOTHING;

WITH permission_ids AS (
INSERT INTO permissions (http_method, path)
VALUES
    ('GET', '/api/roles$'),
    ('POST', '/api/roles$'),
    ('GET', '/api/roles/\d+')
    ON CONFLICT (http_method, path)
    DO NOTHING
    RETURNING id
),

-- Lấy id của vai trò 'ADMIN'
    admin_id AS (
SELECT id
FROM roles
WHERE name = 'ADMIN'
    )

-- Chèn vào bảng role_permissions
INSERT INTO role_permissions (role_id, permission_id)
SELECT admin_id.id, permission_ids.id
FROM admin_id, permission_ids
    ON CONFLICT (role_id, permission_id) DO NOTHING;
