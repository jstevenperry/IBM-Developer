/*
   Copyright 2018 Makoto Consulting Group, Inc.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
// require
const path = require('path');
const express = require('express');
const cookieParser = require('cookie-parser');
const createError = require('http-errors');

// local modules
const indexRouter = require('./routes/index');
const listsRouter = require('./routes/lists');
const restRouter = require('./routes/rest');

// Create Express application
const app = express();

// Install Middleware
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(express.static(path.join(__dirname, 'public')));
app.use(cookieParser());

// Path routing
app.use(indexRouter);
app.use('/lists', listsRouter);
app.use('/rest', restRouter);

// View engine
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'pug');

// Error handling
app.use(function(req, res, next) {
  // catch 404 and forward to error handler
  next(createError(404));
});
app.use(function(err, req, res, next) {
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};
  res.status(err.status || 500);
  res.render('error');
});

module.exports = app;
