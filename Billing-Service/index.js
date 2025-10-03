
import express from "express";
import bootstrap  from "./src/index.Router.js";
import dotenv from "dotenv";
dotenv.config({ path: "./config/.env" });
const app = express();

bootstrap(app,express);

const port = process.env.PORT || 5000;

app.listen(port,()=>{
    console.log(`Server is listening on port.......... ${port}`);
});