var express = require('express');
var router = express.Router();
var userController = require('../controllers/UserController');

router.route('/user').post(userController.registerUser);
router.route('/users').get(userController.getAllUsers);
router.route('/login').post(userController.logIn);
router.route('/users').delete(userController.deleteAllUsers);

module.exports = router;