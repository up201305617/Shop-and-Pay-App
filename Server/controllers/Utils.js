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

exports.getCorrectMonth = function(month){
    if(month == 0){
        return 1;
    }
    else if(month == 1){
        return 2;
    }
    else if(month == 2){
        return 3;
    }
    else if(month == 3){
        return 4;
    }
    else if(month == 4){
        return 5;
    }
    else if(month == 5){
        return 6;
    }
    else if(month == 6){
        return 7;
    }
    else if(month == 7){
        return 8;
    }
    else if(month == 8){
        return 9;
    }
    else if(month == 9){
        return 10;
    }
    else if(month == 10){
        return 11;
    }
    else if(month == 11){
        return 12;
    }
}