//Express
const express = require('express')
const app = express()
const http = require('http')
const expressServer = http.createServer(app)

//SocketIO
const { Server } = require("socket.io")
const io = new Server(expressServer)

//Exports
module.exports.io = io
module.exports.expressServer = expressServer