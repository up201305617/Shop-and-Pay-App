var mongoose = require('mongoose');
var Schema = mongoose.Schema;
var bcrypt = require('bcrypt-nodejs');
var validators = require('mongoose-validators');
var SALT_WORK_FACTOR = 10;

var UserSchema = new Schema({
    name:{
        type: String,
        required: 'The User must have a name.'
    },
    address:{
        type: String,
        required: 'The User must have an address.'
    },
    nif:{
        type: String,
        unique: true,
        required: "The User must have a NIF."
    },
    email: {
        type: String,
        unique: true,
        required: 'The User must have an email.',
        validate: validators.isEmail()
    },
    password: {
        type: String,
        required: 'The User must have a password.'
    },
    public_key:{
        type: String
    },
    cctype:{
        type: String,
        required: true
    },
    ccnumber: {
        type: String,
        required: 'A CreditCard must have a number.',
        validate: validators.isLength(16)
    },
    ccvalidity: {
        type: String,
        required: 'A CreditCard must have a validity date.'
    }
});

UserSchema.pre('save',function(next){
    var user = this;
    // only hash the password if it has been modified (or is new)
    if(!user.isModified('password')){
        next();
    }
    // generate a salt
    bcrypt.genSalt(SALT_WORK_FACTOR, function(err, salt) {
        if (err) return next(err);
        // hash the password using our new salt
        bcrypt.hash(user.password, salt, null, function(err, hash) {
            if (err) return next(err);
            // override the cleartext password with the hashed one
            user.password = hash;
            next();
        });
    });
});

UserSchema.methods.comparePassword = function(candidatePassword, cb) {
    bcrypt.compare(candidatePassword, this.password, function(err, isMatch) {
        if (err){
            return cb(err);
        } 
        cb(null, isMatch);
    });
};

module.exports = mongoose.model('User', UserSchema, 'User');