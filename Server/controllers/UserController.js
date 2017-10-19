var User = require('../models/User');

exports.registerUser = function(req,res){
    var user = new User;
    user.name = req.body.name;
    user.address = req.body.address;
    user.nif = req.body.nif;
    user.email = req.body.email;
    user.password = req.body.password;
    user.creditcard = req.body.creditcard;
    user.save(function(err) {
        if (err) {
            console.log("Entra aqui");
            res.send(err);
        }
        else {
            console.log(req.body.nif);
            res.json({ message: 'User created with email: ' + req.body.email + '.' });
        }
    })
};

exports.getAllUsers = function(req,res){
    User.find(function(err,users){
        if(err) {
            res.send(err);
        }
        else {
            res.json(users);
        }
    });
};

exports.deleteAllUsers = function(req,res){
    User.remove({},function(err){
        if(err){
            res.send(err);
        }
        else{
            res.json({success: true, message: "All users are removed!"});
        }
    });
};

exports.logIn = function(req,res){
    User.findOne({email: req.body.email}, function(err, user){
        if(err){
            res.send(err);
        }
        if(!user){
            res.json({success: false, message: "User not found!"});
        }
        else if(user){
            res.json({success: true, message: "User "+req.body.email+" found!"});
        }
    });
};