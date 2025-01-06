INSERT INTO roles (name)
VALUES ('USER')
    ON CONFLICT (name) DO NOTHING;

WITH permission_ids AS (
INSERT INTO permissions (http_method, path)
VALUES
    -- Book
    ('GET', '/api/books/\d+'),
    ('POST', '/api/books/filter$'),
    ('POST', '/api/books/collections$'),

    -- Author
    ('GET', '/api/authors/\d+'),
    ('POST', '/api/authors/filter$'),
    ('GET', '/api/authors/books/\d+'),

    -- Collection
    ('POST', '/api/collections$'),
    ('DELETE', '/api/collections$'),
    ('POST', '/api/collections/filter$'),

    -- Comment
    ('GET', '/api/comments/\d+'),
    ('POST', '/api/comments$'),
    ('PUT', '/api/comments/\d+'),
    ('DELETE', '/api/comments/\d+'),
    ('POST', '/api/comments/filter$'),

    -- Favorite
    ('POST', '/api/favorites$'),
    ('DELETE', '/api/favorites$'),
    ('DELETE', '/api/favorites/\d+'),
    ('POST', '/api/favorites/filter$'),

    -- Payment
    ('GET', '/api/payments/\d+'),

    -- Price
    ('POST', '/api/prices/filter$'),

    -- Rating
    ('POST', '/api/ratings$'),
    ('DELETE', '/api/ratings$'),
    ('PUT', '/api/ratings$'),
    ('GET', '/api/ratings$'),

    -- Subscription
    ('GET', '/api/subscriptions/\d+'),
    ('POST', '/api/subscriptions/filter$'),

    -- Users
    ('POST', '/api/users/changePassword$')

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
