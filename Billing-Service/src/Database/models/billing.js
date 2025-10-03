import { model, Schema, Types } from "mongoose";


const billingSchema = new Schema({
    userId:{
        type: String,
        required:true
    },

    description:{
        type:String,
        required:true
    },
    price:{
        type:Number,
        required:true
    }
    
},{
    timestamps:true
});


const billingModel = model("Billing",billingSchema);

export default billingModel;