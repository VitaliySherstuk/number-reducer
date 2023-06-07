# number-reducer
A service responsible for reducing list of print number pages and providing friendly view of them.

## Running locally

### Run a Docker
- install Docker from Management Software Center
- clone project from git repo: https://github.com/VitaliySherstuk/number-reducer
- run docker-compose (/docker/docker-compose.yml)
    - cd docker/
    - docker-compose up -d

### Run app by Spring Boot
- run "NumberReducerApplication"
- call endpoint: GET http://localhost:8080/printer/reducedPageNumbers?rawPageNumbers=${listPages}
  Example: GET http://localhost:8080/printer/reducedPageNumbers?rawPageNumbers=1,2,3,4,5

## Deployment on DEV env

### Docker registry
vitaliysherstuk/number-reducer

### Build docker image
- docker build -f ./docker/Dockerfile -t vitaliysherstuk/number-reducer .
- docker login
- docker push vitaliysherstuk/number-reducer:{version}

### Deploy to DEV env
- kubectl apply -f /number-reducer/k8s/deployment.yaml
- kubectl rollout status deployment number-reducer-deployment  -- > check status
- check application by Swagger


## Testing of app

### OpenAPI Docks
http://{host}:{port}/api-docs


### Swagger
http://{host}:{port}/swagger-ui/index.html#