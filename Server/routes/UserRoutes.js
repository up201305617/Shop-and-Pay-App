var express = require('express');
var router = express.Router();
var userController = require('../controllers/UserController');

router.route('/user').post(userController.registerUser);

module.exports = router;