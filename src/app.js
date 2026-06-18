require("dotenv").config();

const express =
    require("express");

const titularesRoutes =
    require("./routes/titularesRoutes");

const errorHandler =
    require("./middleware/errorHandler");

const app = express();

app.use(
    express.json({
        limit: "50mb"
    })
);

app.use(
    "/api/titulares",
    titularesRoutes
);

app.use(errorHandler);

module.exports = app;