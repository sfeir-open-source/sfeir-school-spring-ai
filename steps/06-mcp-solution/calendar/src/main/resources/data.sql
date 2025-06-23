INSERT INTO
    CALENDAR_ITEM (id, detail)
VALUES (
        RANDOM_UUID (),
        JSON '{
    "day": "2025-06-22",
    "from": "11:00:00",
    "to": "12:00:00",
    "description": "Design review meeting"
  }'
    ),
    (
        RANDOM_UUID (),
        JSON '{
    "day": "2025-06-23",
    "from": "13:30:00",
    "to": "14:15:00",
    "description": "One-on-one coaching"
  }'
    ),
    (
        RANDOM_UUID (),
        JSON '{
    "day": "2025-06-24",
    "from": "16:00:00",
    "to": "17:00:00",
    "description": "Project demo to stakeholders"
  }'
    ),
    (
        RANDOM_UUID (),
        JSON '{
    "day": "2025-06-25",
    "from": "10:00:00",
    "to": "11:30:00",
    "description": "Sprint planning session"
  }'
    );