# WSO2 Micro Integrator

This repository builds the WSO2 Micro Integrator distribution and provides the 
release artifacts as `wso2mi-<version>.zip` file. Currently, the `wso2mi-<version>.zip`
file is uploaded manually for every release.

The release artifacts are then used by the
[ei-docker](https://github.com/integonch/docker-ei) project to build the Docker
images.

## How to Build the `wso2mi-<version>.zip`?

- Install Java 8 and Maven
- `mvn clean package`

The `wso2mi-<version>.zip` file will be created in the `distribution/target`
directory.
