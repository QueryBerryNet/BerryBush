const io = require('socket.io-client');
const socket = io.connect('http://localhost:3000', {reconnect: true});

// Add a connect listener
socket.on('connect', function (msg) {
    console.log('Connected!');
    socket.emit('registration', 'data is emitted !')
});
