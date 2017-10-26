var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var ShopListSchema = new Schema({

    email: {
        type: String,
        unique: true,
        required: 'The User must have an email.',
        validate: validators.isEmail()
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
    ]
});

module.exports = mongoose.model('ShopList', ShopListSchema, 'ShopList');