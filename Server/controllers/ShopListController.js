var ShopList = require("../models/ShopList");

exports.insertShopList = function(req,res){
    var list = new ShopList;
    list.email = req.body.email;
    list.products = req.body.products;
    list.save(function(err) {
        if (err) {
            res.send(err);
        }
        else {
            res.json({ message: 'List added.', success: true });
        }
    });
};

exports.getAllLists = function(req,res){
    ShopList.find(function(err,lists){
        if(err) {
            res.send(err);
        }
        else {
            res.json(lists);
        }
    });
};