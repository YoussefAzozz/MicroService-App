// src/gRPC/grpc-service.js
import grpc from "@grpc/grpc-js";
import protoLoader from "@grpc/proto-loader";
import path from "node:path";
import { fileURLToPath } from "node:url";
import billingModel from "../Database/models/billing.js";

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

// Always resolve from *this file’s* directory:
const protoPath = path.resolve(__dirname, "../proto/billing_service.proto");

const packageDef = protoLoader.loadSync(protoPath, {
  keepCase: true,
  longs: String,
  enums: String,
  defaults: true,
  oneofs: true,
});
const grpcPkg = grpc.loadPackageDefinition(packageDef).billingService;

export function startGrpcServer() {
  const server = new grpc.Server();
  server.addService(grpcPkg.BillingService.service, {
    CreateBillingAccount: createBillingAccount,
  });

  server.bindAsync(
    `0.0.0.0:${process.env.GRPC_PORT || 9001}`,
    grpc.ServerCredentials.createInsecure(),
    (err, port) => {
      if (err) {
        console.error("Failed to bind server:", err);
        return;
      }
      console.log(`gRPC server started, listening on port ${port}`);
    }
  );
}

async function createBillingAccount(call, callback) {
  try {
    const bill = { ...call.request, price: 100.5, description: "General Checkup" };
    await billingModel.create(bill);

    const response = {
      accountId: `ACC-${bill.userId}`,
      status: "success",
      price: bill.price, // number
    };
    callback(null, response);
  } catch (e) {
    console.error(e);
    callback({
      code: grpc.status.INTERNAL,
      message: "Failed to create billing account",
    });
  }
}
