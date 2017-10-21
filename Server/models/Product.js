var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var ProductSchema = new Schema({
    price: {
        type: String,
        min: 0,
        required: 'A Product must have a price.'
    },
    model: {
        type: String,
        required: "A Product must have a model."
    },
    maker:{
        type: String,
        required: "A Product must have a maker."
    },
    category:{
        type: String,
        required: "A Product must have a category."
    },
    barcode:{
        type: String,
        required: "A Product must have a bar code."
    }
});

module.exports = mongoose.model('Product', ProductSchema, 'Product');