var express = require('express');
var router = express.Router();
var productController = require('../controllers/ProductController');

router.route('/product').post(productController.insertProduct);
router.route('/products').get(productController.getAllProducts);
router.route('/product').get(productController.getProductByBarcode);

module.exports = router;