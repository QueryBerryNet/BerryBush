const express = require('express')
const app = express()
const http = require('http')
const server = http.createServer(app)

const { Server } = require("socket.io")
const io = new Server(server)

const { ManifestHandler } = require("./modules/manifestHandler")
const manifestHandler = new ManifestHandler()

const bodyParser = require("body-parser")
const pjson = require('../package.json');

app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());

app.get('/api/v1/app-info', (req, res) => {
  res.send({
    version: pjson.version,
    dependencies: pjson.dependencies,
    name: pjson.name
  })
})

app.get('/api/v1/iot/socket/clients/is-online/:id', (req, res) => {
  //todo
})

/**
 * route для вывода все устройств из бд
 */
app.get('/api/v1/iot/socket/clients/get-all', (req, res) => {
  manifestHandler.selectManifest(null, (result) => {
    res.send(result)
  })
})

/**
 * route для обновления манифеста устройства
 * Пример запроса:
 * {
 *  "id": "WE6ihcXPQdMAqLPQAAAD",
 *  "newState": "closed"
 * }
 */
app.post('/api/v1/iot/socket/clients/update-manifest', (req, res) => {
  let body = req.body
  let newState = body.newState
  let id = body.id

  io.to(id).emit('updateManifestState', newState)
  res.send("Device state updated")
})

io.on('connection', (client) => {  
  console.log(`user connected ${client.id}`)

  client.on('insertManifest', (data) => {
    data.socketId = client.id
    manifestHandler.insertManifest(data, (newManifest) => {
      client.emit("setMongoId", newManifest)
      client.emit("setSocketId", client.id)
    })
  })

  client.on('updateManifest', (data) => {
    manifestHandler.updateManifest(client.id, data)
  })

  client.on('disconnect', () => {
    console.log(`user disconnected ${client.id}`)
    manifestHandler.deleteManifest(client.id)
  })
})

server.listen(3000, () => {
  console.log('listening on *:3000');
})