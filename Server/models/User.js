var mongoose = require('mongoose');
var Schema = mongoose.Schema;
var bcrypt = require('bcrypt-nodejs');
var validators = require('mongoose-validators');

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
        required: 'The User must have a password.',
    }/*,
    creditcard: [
        {
            type: {
                type: String,
                required: true
            },
            number: {
                type: String,
                required: 'A CreditCard must have a number.',
                validate: validators.isLength(16)
            },
            validity: {
                type: Date,
                required: 'A CreditCard must have a validity date.'
            }
        }
    ]*/
});

module.exports = mongoose.model('User', UserSchema, 'User');