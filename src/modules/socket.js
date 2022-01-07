const { io } = require("./libImport");

/*
todo: jsdoc!

iot -> умное устройство
server -> главный сервер
bb -> berrybrush

iot -> server. 
  - bbRegistration (bb method): 
    - сервер проверяет iot, а именно его конфиг и если все верно разрешает ему работать дальше
    - и присваивает ему ключ 

iot -> server.
  - bbReceive (bb method):
    - Сервер принимает данные от iot

*/

class bbIotClients {
  constructor () {
    this.clients = []
    this.clientsMapCount = 0
  }

  addClient(socket) {
    this.clients.push(socket)
  }

  getClientLastConnect(socket) {
    //magic
  }

  mapClientToDb(socket, id) {
    //magic
  }

  removeClient(socket) {
    this.clients.splice(this.clients.indexOf(socket), 1);
  }

  getClientsCount() {
    return this.clients.length()
  }

}

function bbRegistration() {}

function bbReceive() {}

const iotClients = new bbIotClients()

io.on('connection', (socket) => {  
  console.log('user connected')

  iotClients.addClient(socket)

  socket.on('registration', (data) => {
    console.log(`data received is '${data}'`)
  })

  socket.on('disconnect', function() {
    iotClients.removeClient(socket)
  })

})

