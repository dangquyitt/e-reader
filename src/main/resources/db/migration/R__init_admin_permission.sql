INSERT INTO roles (name)
VALUES ('ADMIN')
ON CONFLICT (name) DO NOTHING;

WITH permission_ids AS (
INSERT INTO permissions (http_method, path)
VALUES
    ('POST', '/api/roles$'),
    ('PUT', '/api/roles/\d+'),
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

    -- BookCollection
    ('POST', '/api/bookCollections$'),
    ('DELETE', '/api/bookCollections$'),
    ('POST', '/api/bookCollections/filter$'),

    -- BookTag
    ('POST', '/api/bookTags$'),
    ('DELETE', '/api/bookTags$'),

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

    -- Plan
    ('GET', '/api/plans$'),
    ('POST', '/api/plans/filter$'),
    ('POST', '/api/plans$'),
    ('PUT', '/api/plans$'),
    ('DELETE', '/api/plans/\d+'),

    -- Price
    ('POST', '/api/prices$'),
    ('POST', '/api/prices/filter$'),

    -- Rating
    ('POST', '/api/ratings$'),
    ('DELETE', '/api/ratings$'),
    ('PUT', '/api/ratings$'),
    ('GET', '/api/ratings$'),

    -- Subscription
    ('POST', '/api/subscriptions$'),
    ('GET', '/api/subscriptions/\d+'),
    ('POST', '/api/subscriptions/filter$'),
    ('DELETE', '/api/subscriptions$'),

    -- Tag
    ('POST', '/api/tags$'),
    ('DELETE', '/api/tags$'),
    ('POST', '/api/tags/filter$'),

    -- Users
    ('POST', '/api/users/changePassword$')

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
