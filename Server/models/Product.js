var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var ProductSchema = new Schema({
    price: {
        type: Number,
        min: 0,
        required: 'A Product must have a price.'
    },
    model: {

    }
});

module.exports = mongoose.model('Product', ProductSchema, 'Product');