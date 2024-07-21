import mongoose from 'mongoose';
import Address from './address.model.js';

const userSchema = new mongoose.Schema({
    name: {
        type: String,
        required: true,
    },
    phone: {
        type: String,
        required: true,
        unique: true,
    },
    email: {
        type: String,
        required: true,
        unique: true,
    },
    addresses: {
        type: [Address.schema],
        required: true,
    },
    password:{
        type:String,
        required:true,
        select:false,
    },
    gender: {
        type: String,
        required: true,
        
    },
    userType:{
        type:String,
        required:true
    },
    image:{
        type:String,
    }


});

const User = mongoose.model('User', userSchema);

export default User;
