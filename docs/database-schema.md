# Database Schema

## `users`

| Column | Type | Notes |
|---|---|---|
| `id` | UUID | Primary key |
| `email` | VARCHAR(320) | Unique, indexed |
| `username` | VARCHAR(100) | Optional display name |
| `password` | VARCHAR(100) | BCrypt hash |
| `role` | VARCHAR(30) | `ROLE_USER` or `ROLE_ADMIN` |
| `created_at` | TIMESTAMP WITH TIME ZONE | Audited |
| `updated_at` | TIMESTAMP WITH TIME ZONE | Audited |

## `url_mappings`

| Column | Type | Notes |
|---|---|---|
| `id` | UUID | Primary key |
| `original_url` | VARCHAR(2048) | Target URL |
| `short_code` | VARCHAR(20) | Unique short code |
| `click_count` | BIGINT | Incremented on redirect |
| `last_accessed_at` | TIMESTAMP WITH TIME ZONE | Last redirect time |
| `expiration_date` | TIMESTAMP WITH TIME ZONE | Optional expiration |
| `active` | BOOLEAN | Soft disable switch |
| `user_id` | UUID | FK to `users.id` |
| `created_at` | TIMESTAMP WITH TIME ZONE | Audited |
| `updated_at` | TIMESTAMP WITH TIME ZONE | Audited |

Indexes:
- `idx_url_mapping_short_code`
- `idx_url_mapping_user_created_at`
- `idx_url_mapping_original_url`

## `click_events`

| Column | Type | Notes |
|---|---|---|
| `id` | UUID | Primary key |
| `accessed_at` | TIMESTAMP WITH TIME ZONE | Click timestamp |
| `browser` | VARCHAR(100) | Parsed from user-agent |
| `operating_system` | VARCHAR(100) | Parsed from user-agent |
| `ip_address` | VARCHAR(100) | Request IP |
| `country` | VARCHAR(100) | Reserved for geo lookup |
| `user_agent` | VARCHAR(512) | Raw user-agent |
| `url_mapping_id` | UUID | FK to `url_mappings.id` |

Indexes:
- `idx_click_event_url_accessed_at`
- `idx_click_event_ip`

## Redis Keys

| Key Pattern | Purpose |
|---|---|
| `auth:session:{sessionId}` | Serialized refresh session |
| `auth:user-sessions:{userId}` | Session ID set per user |
| `auth:blacklist:{tokenId}` | Blacklisted access-token IDs |
