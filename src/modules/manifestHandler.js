const { insert, update, remove, select } = require("./db")

class ManifestHandler {

  //insert
  insertManifest(manifest, callback) {
    insert(manifest, (mongoData) => {
      callback(mongoData)
    }) 
  }

  //delete
  deleteManifest(socketId) {
    remove({socketId: socketId})
  }

  //update
  updateManifest(socketId, manifest) {
    update({socketId: socketId}, manifest)
  }

  //select
  selectManifest(socketId=null, callback) {
    if (socketId != null) {
      select({socketId: socketId}, (result) => {
        callback(result)
      })
    } else {
      select({}, (result) => {
        callback(result)
      })
    }
  }
}

module.exports.ManifestHandler = ManifestHandler