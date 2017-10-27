var User = require('../models/User');

exports.registerUser = function(req,res){
    var user = new User;
    user.name = req.body.name;
    user.address = req.body.address;
    user.nif = req.body.nif;
    user.email = req.body.email;
    user.password = req.body.password;
    user.cctype = req.body.cctype;
    user.ccnumber = req.body.ccnumber;
    user.ccvalidity = req.body.ccvalidity;
    user.public_key = req.body.public_key;
    console.log(req.body.public_key);
    user.save(function(err) {
        if (err) {
            res.json({success:false});
        }
        else {
            res.json({success:true, message: 'User created with email: ' + req.body.email + '.' });
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
            user.comparePassword(req.body.password, function(err,isMatch){
                if(err){
                    throw err;
                }
                else if(!isMatch){
                    res.json({success: false, message: "Wrong Password"});
                }
                else{
                    res.json({success: true, message: "LogIn", name: user.name});
                }
            });
        }
    });
};