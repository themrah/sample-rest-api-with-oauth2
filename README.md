# sample-rest-api-with-oauth2
This projects is a poc that implements bff, api-gateway and resource service that authenticated with cloudfoundry uaa component. Bff routes requests to api-gateway and api-gateway check authentication and if authentication is ok then route request to resource service. Bff and resource-service are implemented with Spring Boot and api-gateway is implemented with Zuul.
