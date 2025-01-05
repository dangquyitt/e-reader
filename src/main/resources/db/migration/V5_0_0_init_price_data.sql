INSERT INTO "public"."prices"
("amount", "currency", "plan_id", "features", "duration_unit", "duration", "metadata", "effective_date")
VALUES
    (3.49, 'USD', (SELECT id FROM plans WHERE name = 'Basic'),
     ARRAY['Access all features', 'Continuously updated books', 'Unlimited access to books', 'Unlimited reading time'],
    'DAY', 7,
    '{"buttonText": "Sign up for free", "buttonVariant": "outlined", "buttonColor": "primary"}',
    now()),

    (12.99, 'USD', (SELECT id FROM plans WHERE name = 'Standard'),
     ARRAY['Includes Basic Plan', 'For users with limited reading time','Best-selling', 'Worth trying', 'Save 13.4% compared to daily plan', 'Email support'],
    'MONTH', 1,
    '{"subheader": "Recommended", "buttonText": "Start now", "buttonVariant": "contained", "buttonColor": "secondary"}',
    now()),

    (149.99, 'USD', (SELECT id FROM plans WHERE name = 'Premium'),
     ARRAY['Includes Basic Plan', 'For diligent users', 'Save 18% compared to daily plan', 'Direct support from the team'],
    'YEAR', 1,
    '{"buttonText": "Contact us", "buttonVariant": "outlined", "buttonColor": "primary"}',
    now());
