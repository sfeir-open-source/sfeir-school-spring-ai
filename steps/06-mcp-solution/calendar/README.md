# Getting Started

## Database

```yaml
spring:
  h2:
    console:
      enabled: true
  datasource:
    url: jdbc:h2:mem:calendar
    username: sa
    password:
    driverClassName: org.h2.Driver
    hibernate:
      ddl-auto: create-drop
```

```sql
INSERT INTO CALENDAR_ITEM_JPA_ENTITY (id, detail) VALUES
(
  '550e8400-e29b-41d4-a716-446655440000',
  JSON '{
    "day": "2025-06-21",
    "from": "09:00:00",
    "to": "10:00:00",
    "description": "Morning stand‑up meeting"
  }'
)
```
