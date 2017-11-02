var mongoose = require('mongoose');
var Schema = mongoose.Schema;
var validators = require('mongoose-validators');

var ShopListSchema = new Schema({

    email: {
        type: String,
        required: 'The User must have an email.',
        validate: validators.isEmail()
    },
    totalPrice:{
        type: String
    },
    products: [
        {
            maker: {
                type: String,
                required: 'A product must have a maker.'
            },
            model: {
                type: String,
                required: 'A product must have a model.'
            },
            price: {
                type: String,
                required: 'A products must have a price.'
            }
        }
    ],
    UUID:{
        type: String,
        unique: true
    }
});

module.exports = mongoose.model('ShopList', ShopListSchema, 'ShopList');