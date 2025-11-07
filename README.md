<h1 align="center" style="font-size: 40px;">🚀 MicroService App – Distributed Healthcare System</h1>

This repository contains a simple and modular healthcare microservices system.
Each service runs on its own, communicates through the gateway, and focuses on a single responsibility.
The goal is to keep development simple, organized, and scalable.

📌 System Architecture

Below is the full architecture of the project, showing how each service communicates:

<h2 align="center" style="font-size: 300px;">🖼 Architecture Diagram</h2>

![System Architecture](./Architectrue.png)


<h2>📚 Overview</h2>
<h4>The system includes multiple independent services:</h4>
✅ API Gateway (Spring Cloud Gateway)

Handles routing, JWT validation, and forwarding requests to the correct microservice.

✅ Auth Service
Provides user registration, login, JWT issuance, and Google OAuth login.
Data is stored in PostgreSQL.

✅ Patient Service
Stores and manages patient data.
Uses PostgreSQL for persistence.

✅ Billing Service
Processes billing actions using gRPC.
Stores data in MongoDB.

✅ Analytics Service
Consumes Kafka events to generate insights.

✅ Notification Service
Consumes Kafka events to send emails.

✅ Docker Compose Layer
Starts all services with one command and wires all dependencies.

📂 Repository Structure
MicroService-App/
│
├── API-Gateway/
├── Auth-Service/
├── Billing-Service/
├── Notification-Service/
├── Analytics-Service/
├── Patient-Management/
│
├── Docker-Compose/
│   ├── docker-compose.yml
│   └── environment templates
│
├── .gitignore
└── README.md



⚙️ Technology Stack

Java / Spring Boot

Node.js

gRPC

PostgreSQL

MongoDB

Kafka

Docker + Docker Compose

Spring Cloud Gateway

Gmail API (SMTP)
