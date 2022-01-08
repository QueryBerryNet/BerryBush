const { MongoClient, ObjectId } = require('mongodb');

const url = 'mongodb://localhost:27017'
const client = new MongoClient(url)
const dbName = 'BerryBrushDevices'
const collectionName = 'devices'

//todo: jsdoc

async function insert(data, callback=null) {
  await client.connect()
  const result = await client.db(dbName)
    .collection(collectionName)
    .insertOne(data)
  if (callback) {
    callback(result)
  }
  client.close()
}

async function select(filter, callback=null) {
  await client.connect()
  const result = await client.db(dbName)
    .collection(collectionName)
    .find(filter).toArray()
  if (callback) {
    callback(result)
  }
  client.close()
}

async function update(filter, data, callback=null) {
  delete data._id
  await client.connect()
  const result = await client.db(dbName)
    .collection(collectionName)
    .updateOne(filter, { $set: data })
  if (callback) {
    callback(result)
  }
  client.close()
}

async function remove(filter, callback=null) {
  await client.connect()

  const result = await client.db(dbName)
    .collection(collectionName)
    .deleteOne(filter)
  if (callback) {
    callback(result)
  }
  client.close()
}

module.exports.insert = insert
module.exports.select = select
module.exports.update = update
module.exports.remove = remove