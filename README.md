# GigCampus Backend

Name: Ntuyenabo Uwayezu  
Student ID: 27158  
Project: GigCampus

## Run the API

1. Create the PostgreSQL database:

```sql
CREATE DATABASE gig_campus;
```

2. If your PostgreSQL username or password is different, update `src/main/resources/application.properties` or set `DB_USER` and `DB_PASSWORD`.

3. From the project root, compile the project:

```powershell
.\mvnw.cmd clean compile
```

4. Start the API:

```powershell
.\mvnw.cmd spring-boot:run
```

5. The API runs at:

```text
http://localhost:8080
```

6. Import `gig-campus-midterm.postman_collection.json` into Postman and set the collection ID variables after creating the seed data through the save endpoints.
