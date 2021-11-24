import { MongoClient, ObjectId } from 'mongodb'

const url = 'mongodb://localhost:27017'
const client = new MongoClient(url)
const dbName = 'BerryBrushDevices'
const collectionName = 'devices'

export async function insert(data, callback=null) {
  await client.connect()
  const result = await client.db(dbName)
    .collection(collectionName)
    .insertOne(data)
  if (callback) {
    callback(result)
  }
  client.close()
}

export async function select(data, callback=null) {
  await client.connect()
  const result = await client.db(dbName)
    .collection(collectionName)
    .find(data).toArray()
  if (callback) {
    callback(result)
  }
  client.close()
}

export async function selectById(id, callback=null) {
  await client.connect()

  const idDict = {_id: new ObjectId(id)}

  const result = await client.db(dbName)
    .collection(collectionName)
    .find(idDict).toArray()
  if (callback) {
    callback(result)
  }
  client.close()
}

//{ _id: new ObjectId("619c588bf29ff679a77846a9")}, { $set: { _id: new ObjectId("619c588bf29ff679a77846a9"), hi: 'test' } }
export async function updateById(id, data, callback=null) {
  await client.connect()

  const idDict = {_id: new ObjectId(id)}
  let newData = data

  newData._id = idDict._id
  const result = await client.db(dbName)
    .collection(collectionName)
    .updateOne(idDict, { $set: newData })
  if (callback) {
    callback(result)
  }
  client.close()
}

export async function removeById(id, callback=null) {
  await client.connect()
  const result = await client.db(dbName)
    .collection(collectionName)
    .deleteOne(id)
  if (callback) {
    callback(result)
  }
  client.close()
}

