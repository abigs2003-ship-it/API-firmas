const path = require("path");

require("dotenv").config({
    path: path.resolve(__dirname, "../.env")
});

const app = require("./app");

const PORT = process.env.PORT || 8080;

app.listen(PORT, () => {
    console.log(`Servidor iniciado en puerto ${PORT}`);
});