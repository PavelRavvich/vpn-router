## Configure VPN Service like Bird *ip.conf

Get all routes by Hostname (or URL) and write to *.conf file. 

Configuration in `vpn-router/src/main/resources/application.properties`

- `vpn.routes.config` - VPN config location with routes.
- `vpn.reconfigure.bash.cmd` bash command for reconfigure VPN.

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


