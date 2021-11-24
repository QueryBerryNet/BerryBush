import { wsServer, onConnect } from './deviceWorker'
import { select, insert, updateById, removeById, selectById } from './mongoWrapper'
import pjson from './package.json'
import express from 'express'

const app = express()
const port = pjson.env.express

wsServer.on('connection', onConnect)

app.get('/devices', (req, res) => {
  select({}, (x) => {
    res.send(x)
  })
})

app.put('/devices', (req, res) => {
  
})

app.listen(port, () => {
  console.log(`BerryBrush websocket server hosted at http://localhost:${pjson.env.websocket}`)
  console.log(`BerryBrush express server hosted at http://localhost:${pjson.env.express}`)
})