var ShopList = require("../models/ShopList");
var User = require("../models/User");
var crypto = require('crypto')
var fs = require('fs')
var utils = require('../controllers/Utils');
var uuidv4 = require('../node_modules/uuid/v4')

exports.insertShopList = function(req,res){
    var list = new ShopList;
    var msg = utils.verifyCreditCard(null);
    
    if(msg == "Denied") {
        res.json("Credit Card Denied");
    }
    else {
        list.email = req.body.email;
        list.totalPrice = req.body.totalPrice;
        list.products = req.body.products;
        list.UUID = uuidv4();
        var d = new Date();
        var date = d.getDate()+"-"+utils.getCorrectMonth(d.getMonth())+"-"+d.getFullYear();
        var time = d.getHours()+":"+d.getMinutes();
        list.date = date;
        list.time = time;
        list.save(function(err) {
            if (err) {
                console.log(err);
                res.json({success: false});
            }
            else {
                res.json({UUID: list.UUID});
            }
        });
    }
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

exports.getListsByEmail = function(req,res){
    ShopList.find({email: req.params.email},function(err,lists){
        if(err){
            res.send(err);
        }
        else if(lists.length>0){
            var pro="";
            for(var i=0; i<lists.length;i++){
                pro += lists[0].products+"\n";
            }
            res.json({lists: lists});
        }
        else {
            res.json({success: false, message: "Shop Lists Not Founded."});
        }
    });
};

exports.deleteAllLists = function(req,res){
    ShopList.remove({},function(err){
        if(err){
            res.send(err);
        }
        else{
            res.json({success: true, message: "All Products are removed!"});
        }
    });
};

exports.getListByUUID = function(req,res){
    ShopList.findOne({UUID: req.params.UUID},function(err,list){
        if(err){
            res.send(err);
        }
        else if(list){
            res.json({price: list.totalPrice, list: list.products});
        }
        else {
            res.json({success: false, message: "List Not Found."});
        }
    });
};