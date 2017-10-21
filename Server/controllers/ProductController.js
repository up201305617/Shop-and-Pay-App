var Product = require("../models/Product");

exports.insertProduct = function(req,res){
    var product = new Product;
    product.maker = req.body.maker;
    product.price = req.body.price;
    product.model = req.body.model;
    product.barcode = req.body.barcode;
    product.category = req.body.category;
    product.save(function(err) {
        if (err) {
            res.send(err);
        }
        else {
            res.json({ message: 'Product added: ' + req.body.maker + '.' });
        }
    });
};

exports.getAllProducts = function(req,res){
    Product.find(function(err,products){
        if(err) {
            res.send(err);
        }
        else {
            res.json(products);
        }
    });
};