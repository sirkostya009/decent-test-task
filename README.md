# Decent Test Task

Before running, make sure you have `PG_URL` environment variable set in the following format:
`jdbc:postgresql://<host>:<port>/<dbname>?user=<username>&password=<password>`.

To run integration tests, you effectively need to have a test database set up for that, set up `PG_TEST_URL` env var
similarly, but obviously make sure you don't test against the prod database.
