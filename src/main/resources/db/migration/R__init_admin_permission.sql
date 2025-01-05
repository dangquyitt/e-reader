INSERT INTO roles (name)
VALUES ('ADMIN')
ON CONFLICT (name) DO NOTHING;

WITH permission_ids AS (
INSERT INTO permissions (http_method, path)
VALUES
    ('GET', '/api/roles$'),
    ('POST', '/api/roles$'),
    ('GET', '/api/roles/\d+'),
    ('POST', '/api/roles/filter$'),
    ('DELETE', '/api/roles/\d+'),

    -- Book
    ('POST', '/api/books$'),
    ('PUT', '/api/books/\d+'),
    ('GET', '/api/books/\d+'),
    ('POST', '/api/books/filter$'),
    ('DELETE', '/api/books/\d+'),
    ('POST', '/api/books/collections$'),

    -- Author
    ('POST', '/api/authors$'),
    ('PUT', '/api/authors/\d+'),
    ('GET', '/api/authors/\d+'),
    ('POST', '/api/authors/filter$'),
    ('DELETE', '/api/books/\d+'),
    ('GET', '/api/authors/books/\d+'),


    -- BookAuthor
    ('POST', '/api/bookAuthors$'),
    ('DELETE', '/api/bookAuthors$'),

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
