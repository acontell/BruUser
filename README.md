# First thoughts

The application has been built using Adam Bien's JEE8 archetype. It comes with the bare essentials needed to run a Java EE aplication using maven.

The architecture of the application follows the BCE pattern:

Business logic is structured in packages which are subdivided in:

- Boundary: contain REST services and business logic.
- Controller (optional): used to lighten the boundaries when they get too fatten.
- Entity: contains the entities to be persisted in the DB.

# Usage

The application comes with a Docker file that sets up the environment. It also comes with a Shell Script that deploys the application and get everything up and running.

Assuming you're a linux user and you have Docker ready in your machine, just run "buildAndRun.sh".

If you don't feel comfortable with Docker, just deploy the application in your Widlfy application server and try it there.