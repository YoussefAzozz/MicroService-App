import dbconnection from "./Database/connection.js";
import { startGrpcServer } from "./gRPC/grpc-service.js";



const bootstrap = ()=>{
    
    startGrpcServer();

    dbconnection();

}

export default bootstrap;