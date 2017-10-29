exports.verifyCreditCard = function(err){
    if(err) {
        throw err;
    }

    var rand = Math.random();
    if(rand >= 0 && rand <= 0.95){
        return "Authorized";
    }
    else{
        return "Denied";
    }
};