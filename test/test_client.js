const WebSocket = require('ws')
const fs = require('fs')
const myWs = new WebSocket('ws://localhost:9000')
const devicePath = './deviceEnv.json'

function regPostHock(data) {
    let jsonEncoded = JSON.stringify({ deviceId: data.insertedId })
    fs.writeFileSync(devicePath, jsonEncoded);
    console.log(`device id: ${data.insertedId}`)
}

myWs.onopen = function () {
    console.log('connectend');
    if (fs.existsSync(devicePath)) {
        const env = JSON.parse(fs.readFileSync(devicePath));
        console.log(env)
        checkProps(env.deviceId)
        //update(env.deviceId)
    } else {
        reg()
    }
}

myWs.onmessage = function (message) {
    const serverAnswer = JSON.parse(message.data)

    switch (serverAnswer.action) {
        case 'registration':
            console.log('Device id saved')
            regPostHock(serverAnswer.data)
            break
        default:
            break
    }
}

// передовать в метод данные
function update(id) {
    myWs.send(JSON.stringify({ action: 'update', id: id, data: {
        deviceInnerData: {
            temperature: Math.random() * (0 - 36) + 0
        }
    }}))
}

function checkProps(id) {
    myWs.send(JSON.stringify({ action: 'checkprops', id: id }))
}

function reg() {
    myWs.send(JSON.stringify({action: 'registration', 
    data: {
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
    }))
}
