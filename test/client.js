const io = require('socket.io-client');
const socket = io.connect('http://localhost:3000', {reconnect: true});

let baseIotManifest = {
    manifestVersion: "0.0.2",
    iot: {
      verbose: {
        name: "TestClient",
        description: "TestClientDescription",
        clientVersion: "0.0.1"
      },
      state: {
        stateCurrent: "open",
        stateAvailable: ["open", "closed"]
      },
      sensors: []
    },
    socketId: "",
  }

// Add a connect listener
socket.on('connect', function (client) {
  socket.emit('insertManifest', baseIotManifest)
})

socket.on('setMongoId', function (data) {
  baseIotManifest._id = data
})

socket.on('setSocketId', function (data) {
  baseIotManifest.socketId = data
})

socket.on('updateManifestState', function (data) {
  baseIotManifest.iot.state.stateCurrent = data
  socket.emit('updateManifest', baseIotManifest)
})
