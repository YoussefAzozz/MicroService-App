MicroService App – Distributed Healthcare System


This repository contains a modular microservices system designed to support a healthcare workflow.
Each service runs independently and communicates through REST APIs and a gateway layer.

The project follows a clean structure, simple deployment steps, and clear separation between services.

📌 Overview

The system includes several core services:

API Gateway – Central entry point. Handles routing between services.

Auth Service – Manages users, authentication, and authorization.

Billing Service – Manages invoices, payments, and subscription logic.

Notification Service – Sends email, SMS, or system notifications.

Analytics Service – Collects and analyzes metrics across the system.

Patient Management Service – Stores and manages patient data.

Docker Compose Layer – Orchestrates local development and service startup.

Each service is built so that it can run alone or as part of the full system.

📂 Repository Structure

MicroService-App/
│
├── API-Gateway/
├── Analytics-Service/
├── Billing-Service/
├── Notification-Service/
├── Patient-Management/
├── auth-service/
│
├── Docker-Compose/
│   ├── docker-compose.yml
│   └── env templates
│
├── .gitignore
└── README.md

🛠 Technology Stack

Node.js / Express (per service)

PostgreSQL / MongoDB (depending on service)

Docker & Docker Compose

Nginx (reverse proxy and gateway routing)

Kafka (optional; message broker for event-driven actions)

REST API communication

⚙️ How to Run the Project
git clone https://github.com/<username>/MicroService-App.git
cd MicroService-App

2. Prepare environment variables

Each service has an .env.example file.

Copy and adjust it:
cp Billing-Service/.env.postgres Billing-Service/.env
cp auth-service/.env.auth-db auth-service/.env
...

3. Start all services using Docker Compose
cd Docker-Compose
docker-compose up --build

Docker will start:

All microservices

Databases

Nginx gateway

Supporting services

🔌 API Gateway Endpoints

The gateway exposes unified public routes.

/api/auth/...
/api/billing/...
/api/patient/...
/api/notification/...

Each route is forwarded to the correct microservice.

📈 Monitoring & Logs

You can watch logs for any service:
docker-compose logs -f auth-service
docker-compose logs -f billing-service

🔧 Development Guidelines

Keep each service decoupled.

Avoid sharing state between services.

Use separate .env files per service.

Follow consistent naming for routes and controllers.

Add integration tests when adding new endpoints.

🚨 Environment Files

Environment files are ignored by Git.
Each service contains:
.env.postgres
.env.mongo
.env.auth-db
.env.notification
.env.patient.service
.env.kafka

These must be created before running the system.

📦 Deployment

The project supports:

Docker-based deployment

Per-service scaling

Stateless replicas (recommended via Kubernetes or Swarm)

You can scale any service:
docker-compose up --scale auth-service=3
