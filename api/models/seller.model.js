import mongoose from 'mongoose'

const sellerSchema = new mongoose.Schema({
   sellerName: {
      type: String,
      required: true,
   },
   shopName: {
      type: String,
      required: true,
      unique: true,
   },
   email: {
      type: String,
      required: true,
      unique: true,
   },
   password: {
      type: String,
      required: true,
      select: false,
   },
   description: String,
   categories: {
      type: [String],
      required: true,
   },
   phone: {
      type: String,
      required: true,
   },
   address: {
      street: String,
      city: String,
      state: String,
      zipCode: String,
      country: String,
   },
   isVerified: {
      type: Boolean,
      default: false,
   },
   image: {
      type: String,
      required: true,
   },
   rating: {
      type: Number,
      default: 0,
   },
})

const Seller = mongoose.model('Seller', sellerSchema)

export default Seller
