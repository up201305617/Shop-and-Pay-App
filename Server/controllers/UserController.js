var User = require('../models/User');

exports.registerUser = function(req,res){
    var user = new User;
    user.name = req.body.name;
    user.address = req.body.address;
    user.nif = req.body.nif;
    user.email = req.body.email;
    user.password = req.body.email;
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