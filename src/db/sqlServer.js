const sql = require("mssql");

const dbServer = process.env.DB_SERVER;
const [server, port] = dbServer ? dbServer.split(":") : [undefined, undefined];

const config = {
    server: server || dbServer,
    port: port ? parseInt(port, 10) : undefined,
    database: process.env.DB_DATABASE,
    user: process.env.DB_USER,
    password: process.env.DB_PASSWORD,
    options: {
        encrypt: false,
        trustServerCertificate: true
    }
};

if (!config.server) {
    throw new Error("Missing DB_SERVER environment variable.");
}
if (!config.database) {
    throw new Error("Missing DB_DATABASE environment variable.");
}
if (!config.user) {
    throw new Error("Missing DB_USER environment variable.");
}
if (config.password == null) {
    throw new Error("Missing DB_PASSWORD environment variable.");
}

const poolPromise = new sql.ConnectionPool(config)
    .connect()
    .then(pool => {
        console.log("SQL Server connected");
        return pool;
    });

module.exports = {
    sql,
    poolPromise
};