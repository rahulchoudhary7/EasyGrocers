import mongoose from 'mongoose'
import Address from './address.model.js'

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
      match: /.+\@.+\..+/,
   },
   addresses: {
      type: [Address.schema],
      required: true,
   },
   password: {
      type: String,
      required: true,
      select: false,
   },
   gender: {
      type: String,
      required: true,
   },
   userType: {
      type: String,
      required: true,
      default: "user"
   },
   image: {
      type: String,
   },
})

const User = mongoose.model('User', userSchema)

export default User
