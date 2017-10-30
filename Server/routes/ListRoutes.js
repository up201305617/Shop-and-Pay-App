var express = require('express');
var router = express.Router();
var shopListController = require("../controllers/ShopListController");

router.route('/shoplist').post(shopListController.insertShopList);
router.route('/shoplists').get(shopListController.getAllLists);
router.route('/shoplists/:email').get(shopListController.getListsByEmail);
router.route('/shoplists').delete(shopListController.deleteAllLists);
router.route('/shoplist/:UUID').get(shopListController.getListByUUID);

module.exports = router;
