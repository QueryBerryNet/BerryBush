import { WebSocket } from "ws"
import { select, insert, updateById, removeById, selectById } from './mongoWrapper'
import pjson from './package.json'

/*
data example
{
  deviceName: 'test window',
  deviceData: {
    name: 'Window',
    type: 'device'
  },
  ip: '127.0.0.1',
  deviceInnerData: {
      
  },
  props: {
    deviceOptions: ['close', 'open'],
    selectedOption: 'close',
  }
}

{
  deviceName: 'test temp',
  deviceData: {
    name: 'Thermometer',
    type: 'sensor'
  },
  ip: '127.0.0.1',
  deviceInnerData: {
    temperature: '20 c'
  },
  props: {
    deviceOptions: [],
    selectedOption: '',
  }
}

*/

function checkFields(data) {
  //TODO: доделать проверку
}

// регистрация девайса
function registerDevice(ws, data) {
  insert(data, (x) => {
    ws.send(JSON.stringify({action: 'registration', data: x}))
  })
    .then(console.log(`Device created`))
}

// обнавление девайса
function updateDeviceData(ws, data) {
  updateById(data.id, data.data, (x) => {
    ws.send(JSON.stringify({action: 'update', data: x}))
  })
    .then(console.log(`Device ${data.id} updated`))
}

// проверка не изменилось ли состояние девайса
function checkDeviceProps(ws, data) {
  selectById(data.id, (x) => {
    let mongoDict = x[0]
    if (mongoDict.deviceData.type == 'device') {
      //working with data.data
      //TODO: доделать
    } else {
      console.log('Sensor trying to check props :-/')
    }
  })
  .then(console.log(`Props checked`))
}


export const wsServer = new WebSocket.Server({port: pjson.env.websocket})

export function onConnect(wsClient) {
  console.log('Device connected')

  wsClient.on('close', function() {
      console.log('Device disconnected')
  })

  wsClient.on('message', function(message) {
      try {
          const jsonMessage = JSON.parse(message);
          switch (jsonMessage.action) {
            case 'registration':
              registerDevice(wsClient, jsonMessage.data)
              break
            case 'update':
              updateDeviceData(wsClient, jsonMessage)
              break
            case 'checkprops':
              checkDeviceProps(wsClient, jsonMessage)
              break
            default:
              console.log('Unknown command')
              break
          }
      } catch (error) {
          console.log('Error', error)
      }
  })
}
