const sql = require('mssql');

const config = {
    user: process.env.DB_USER || 'sa',
    password: process.env.DB_PASSWORD || 'your_password',
    server: process.env.DB_SERVER || 'localhost',
    database: process.env.DB_DATABASE || 'your_database',
    options: {
        encrypt: false,
        enableArithAbort: true,
    },
};

let poolPromise;

async function getPool() {
    if (!poolPromise) {
        poolPromise = sql.connect(config);
    }
    return poolPromise;
}

async function executeProcedure(procName, inputParams = {}) {
    const pool = await getPool();
    const request = pool.request();
    for (const [name, { type, value }] of Object.entries(inputParams)) {
        request.input(name, type, value);
    }
    const result = await request.execute(procName);
    return result;
}

module.exports = { sql, getPool, executeProcedure };
