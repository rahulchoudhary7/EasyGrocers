import mongoose from 'mongoose'

const adminSchema = new mongoose.Schema({
   adminName: {
      type: String,
      required: true,
   },
   email: {
      type: String,
      required: true,
      unique: true,
      match: /.+\@.+\..+/,
   },
   password: {
      type: String,
      required: true,
      select: false,
   },
   phone: {
      type: String,
      required: true,
   },
   role: {
      type: String,
      enum: ['SUPER_ADMIN', 'ADMIN', 'MODERATOR'],
      default: 'ADMIN',
   },
   userType: {
      type: String,
      required: true,
      default: "ADMIN"
   },
   isActive: {
      type: Boolean,
      default: true,
   },
   permissions: {
      type: [String],
      default: ['read', 'write', 'update'],
   },
   lastLogin: {
      type: Date,
   },
   createdAt: {
      type: Date,
      default: Date.now,
   },
   updatedAt: {
      type: Date,
      default: Date.now,
   }
})

adminSchema.pre('save', function(next) {
   this.updatedAt = Date.now()
   next()
})

const Admin = mongoose.model('Admin', adminSchema)

export default Admin