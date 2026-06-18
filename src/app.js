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

app.use((req, res, next) => {
    console.log("--incoming request--");
    console.log(`Method: ${req.method}, URL: ${req.url}, Body: ${JSON.stringify(req.body)}`);

});
app.use(
    "/api/titulares",
    titularesRoutes
);

app.use(errorHandler);

module.exports = app;