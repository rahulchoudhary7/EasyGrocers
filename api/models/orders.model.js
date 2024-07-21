import mongoose from 'mongoose';
import Item from "./item.model.js";
import Address from "./address.model.js";
import User from './user.model.js';

const orderSchema = new mongoose.Schema({
    orderNumber: {
        type: String,
        required: true,
        unique: true,
    },
    items: {
        type: [Item.schema],
        required: true,
    },
    mrp: {
        type: Number,
        required: true,
    },
    itemTotal: {
        type: Number,
        required: true,
    },
    handlingCharge: {
        type: Number,
        required: true,
    },
    deliveryCharge: {
        type: Number,
        required: true,
    },
    billTotal: {
        type: Number,
        required: true,
    },
    paymentMode: {
        type: String,
        required: true,
    },
    address: {
        type: Address.schema,
        required: true,
    },
    orderPlacedTime: {
        type: Date,
        default: Date.now,
        required: true,
    },
    deliveredTime: {
        type: Date,
        required: true,
    },
    userId: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'User',
        required: true,
    }
});

const Order = mongoose.model('Order', orderSchema);

export default Order;
