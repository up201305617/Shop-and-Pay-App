var express = require('express');
var path = require('path');
var favicon = require('serve-favicon');
var logger = require('morgan');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');

var index = require('./routes/index');
var users = require('./routes/users');
var userRoutes = require('./routes/UserRoutes');
var productRoutes = require('./routes/ProductRoutes');
var listRoutes = require('./routes/ListRoutes');
var Product = require("./models/Product");
var app = express();
var mongoose = require('mongoose');
// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'jade');

// uncomment after placing your favicon in /public
//app.use(favicon(path.join(__dirname, 'public', 'favicon.ico')));
app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

app.use('/', index);
app.use('/users', users);

app.use('/api', userRoutes);
app.use('/api', productRoutes);
app.use('/api', listRoutes);

var MongoDB = mongoose.connect('mongodb://localhost:27017/CMOV1',{ useMongoClient: true });
MongoDB.on('error', function(err) { console.log(err.message); });
MongoDB.once('open', function() {
  console.log("MongoDB connection open");
});

var product = new Product({
  price: "120.99",
  name: "Telemovel",
  model: "YT2",
  maker: "ASUS",
  category: "mobile",
  barcode: "61234567890"
});

var computer = new Product({
  price: "700",
  name: "Computador",
  model: "ideapad",
  maker: "lenovo",
  category: "electronics",
  barcode: "12853478357"
});

var tablet = new Product({
  price: "99.99",
  name: "Tablet",
  model: "Pixis",
  maker: "Alcatel",
  category: "electronics",
  barcode: "83248709823"
});

/*product.save(function(err){
  console.log("Saved");
  if(err){
    console.log(err);
  }
});*/

/*computer.save(function(err){
  console.log("Saved");
  if(err){
    console.log(err);
  }
});*/

/*tablet.save(function(err){
  console.log("Saved");
  if(err){
    console.log(err);
  }
});*/

// catch 404 and forward to error handler
app.use(function(req, res, next) {
  var err = new Error('Not Found');
  err.status = 404;
  next(err);
});

// error handler
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render('error');
});

module.exports = app;
