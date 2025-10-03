import dbconnection from "./Database/connection.js";
import { startGrpcServer } from "./gRPC/grpc-service.js";



const bootstrap = (app,express)=>{
    
    app.use(express.json());
    app.get("/hello",(req,res,next)=>{
        console.log("I am hereeeeee");
        return res.json({message:"Hello Docker Container"});
    });
    
    startGrpcServer();
    


    dbconnection();

}

export default bootstrap;