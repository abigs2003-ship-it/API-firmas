const express = require('express');
const titularesRoutes = require('./routes/titularesRoutes');
const errorHandler = require('./middleware/errorHandler');

const app = express();

app.use(express.json());

app.use('/api/titulares', titularesRoutes);

// Error handler (last middleware)
app.use(errorHandler);

module.exports = app;
