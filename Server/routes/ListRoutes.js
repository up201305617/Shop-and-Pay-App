var express = require('express');
var router = express.Router();
var shopListController = require("../controllers/ShopListController");

router.route('/shoplist').post(shopListController.insertShopList);
router.route('/shoplists').get(shopListController.getAllLists);

module.exports = router;
