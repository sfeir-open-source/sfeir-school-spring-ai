# Calendar MCP server

## Start the server

To start the calendar MCP server, launch the command below:
```sh
./gradlew bootRun
```

Then, it will be accessible from any MCP client at `http://localhost:8080`.

## Dataset
An H2 database will also be available and automatically populated with sample data on startup. This is achieved using a `data.sql` file located in the project, which contains SQL statements to insert initial records. The database is in-memory and resets each time the application restarts, making it ideal for development and testing without any manual setup. 

## Tools
3 tools are exposed:
- Current date and time
- Read calendar/Agenda items
- Add calendar items from user query

