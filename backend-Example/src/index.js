const express = require('express');
const cookieParser = require('cookie-parser');
const HttpStatus = require('http-status-codes');
const cors = require('cors');
const logger = require('morgan');
const app = express();
const server = require('http').createServer(app); 
const filePath = express.static('files');

// Middlewares
app.use(cors());
app.use(express.json({ limit: '50mb' }));
app.use(express.urlencoded({ extended: true, limit: '50mb' }));
app.use(cookieParser());
app.use(logger('dev'));

// Routes
app.post('/api/publicarNoticia', async (request, response) => {
    let body = request.body;

    let multiparty = require('multiparty');
    let form = new multiparty.Form({ maxFieldsSize: 16 * 1024 * 1024 });
    console.log("JSON DE BODY POST :::::: ", body);
    
    form.parse(request, async (err, fields, files) => {     
        console.log("JSON DE MULTIPART ::: ", fields); // AQUI DEBERIA IR UN CLAVE VALOR
        
        // EXAMPLE: { "key": "value" } 
        
        // MY ERROR: {null:["value"]} ?? 
    });

    
    return response.status(HttpStatus.StatusCodes.OK).json({
        type: 'success',
        message: 'Hello world.',
        data: body
    });
});

// Servidor
server.listen(3001, () => {
    console.log('Server listen on port ', 3001);
});