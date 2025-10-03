# FROM node:22-slim

FROM node:22-slim as builder
WORKDIR /app
COPY package*.json ./
RUN npm install && npm cache clean --force
COPY . .
RUN npm run build


FROM node:18-alpine

WORKDIR /app

COPY --from=builder /app .

# Create a non-root user and group to run the application
RUN addgroup -S appgroup && adduser -S appuser -G appgroup &&  \
    # Change ownership of the working directory to the non-root user
    chown -R appuser:appgroup /app


ENV connection="mongodb://root:azozzpass@mongo-db:27017/grpcdb?authSource=admin" \
    PORT=5000 \
    GRPC_PORT=9001

# Switch to the non-root user
USER appuser

EXPOSE 5000 9001

ENTRYPOINT ["node"]

CMD ["index.js"]