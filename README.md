# MovieTrack

MovieTrack is a Spring Boot web app that finds movie metadata and related soundtrack albums (OMDb / TMDB / Spotify integration). The project serves a static frontend from `src/main/resources/static` and exposes a small backend API under `/api`.

It was created by a group of students attending the "Webbtjänster" course in Malmö University. 

Contributions by ;
- M8uwantcocoa
- Alexoo1232
- MojtabaH1
- adamkhachab

---

## Requirements

- Java 17
- Git
- Maven (wrapper provided; use `mvnw.cmd` on Windows)
- Network access to external APIs (OMDb / TMDB / Spotify) if you want real results

---

## Required configuration (API keys)

Provide API keys via environment variables or a local `application.properties` (do NOT commit secrets).

Environment variable names (used in `src/main/resources/application.properties`):
- `OMDB_API_KEY`
- `SPOTIFY_CLIENT_ID`
- `SPOTIFY_CLIENT_SECRET`
- `TMDB_API_KEY`

Alternative: create `src/main/resources/application.properties` locally (this file is ignored by git):

```properties
spring.application.name=MovieTrack
omdb.api.key=YOUR_OMDB_KEY
spotify.client.id=YOUR_SPOTIFY_ID
spotify.client.secret=YOUR_SPOTIFY_SECRET
tmdb.api.key=YOUR_TMDB_KEY