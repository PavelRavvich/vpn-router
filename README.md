## VPN Router

[![CI](https://github.com/PavelRavvich/vpn-router/actions/workflows/ci.yml/badge.svg?branch=prod)](https://github.com/PavelRavvich/vpn-router/actions/workflows/ci.yml)

Get all routes by Hostname (or URL) and write to *.conf file. 

Configuration in `vpn-router/src/main/resources/application.properties`

- `vpn.routes.config` - VPN config location with routes.
- `vpn.reconfigure.bash.cmd` bash command for reconfigure VPN.


#### Requirements:

  - Java v17
  - Gradle v7.4.2
  - Postgres v12 
    - Port `5432` 
    - Database `vpn_router`

#### Build: 
- `$ service postgresql start`
- `$ gradle clean build`
- `$ java -jar vpn-router-0.0.1-SNAPSHOT.jar`

### API

#### Get all registered hosts:
`$ curl -X GET   -H "Content-type: application/json"   -H "Accept: application/json"   -d 'null'   "http://localhost:8080/host/list"`

#### Add new host
`$ curl -X POST   -H "Content-type: application/json"   -H "Accept: application/json"   -d '{"url":"https://stackoverflow.com/"}'   "http://localhost:8080/host/create"`

#### Update host
`$ curl -X POST   -H "Content-type: application/json"   -H "Accept: application/json"   -d '{"id":1}'   "http://localhost:8080/host/update"`

#### Delete host
`$ curl -X POST   -H "Content-type: application/json"   -H "Accept: application/json"   -d '{"url":"https://stackoverflow.com/"}'   "http://localhost:8080/host/delete"`

#### Disable all hosts routes from VPN config
`$ curl -X POST   -H "Content-type: application/json"   -H "Accept: application/json"   -d '{"id": 1}'   "http://localhost:8080/host/disable"`

#### Enable all hosts routes from VPN config
`$ curl -X POST   -H "Content-type: application/json"   -H "Accept: application/json"   -d '{"id": 1}'   "http://localhost:8080/host/enable"`


