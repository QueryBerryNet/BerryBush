const { expressServer } = require("./libImport")

expressServer.listen(3000, () => {
    console.log('server on *3000')
})